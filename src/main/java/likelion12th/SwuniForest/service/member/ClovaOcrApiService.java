package likelion12th.SwuniForest.service.member;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import likelion12th.SwuniForest.service.S3Service;
import likelion12th.SwuniForest.service.member.domain.dto.MemberResDto;
import likelion12th.SwuniForest.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClovaOcrApiService {
    @Value("${naver.service.url}")
    private String url;

    @Value("${naver.service.secretKey}")
    private String secretKey;

    private final S3Service s3Service;

    // 사용자가 요청한 이미지를 통해 ocr api 호출
    public MemberResDto doOcr(MultipartFile imageFile) throws IOException {
        // MultipartFile -> File 전환 하며 로컬에 파일 생성됨
        File uploadFile = s3Service.convert(imageFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        String originalName = uploadFile.getName(); // 파일명
        String filePath = uploadFile.getPath(); // 로컬에 임시 저장된 파일 경로

        // 요청받은 파일의 확장자를 file.getContentType() 으로 조회하면 PNG같은 확장자가 아닌 multipart/form-data 로 들어가서 OCR API 호출 시 400 에러가 발생한다.
        // 저장한 File 객체의 확장자를 불러오기 위해 지정된 메소드가 없기 떄문에 살짝은.. 끼워맞추기 식으로 문자열 추출을 하였다.
        String ext = "";
        int lastDotIndex = uploadFile.getName().lastIndexOf('.');
        if (lastDotIndex > 0) {
            ext = originalName.substring(lastDotIndex + 1);
        }

        // OCR API 호출
        MemberResDto memberRes = callApi("POST", filePath, secretKey, ext);

        // 임시 저장된 파일 삭제
        if (uploadFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }

        return memberRes;
    }

    /**
     * 네이버 ocr api 호출한다
     * @param {string} type 호출 메서드 타입
     * @param {string} filePath 파일 경로
     * @param {string} naver_secretKey 네이버 시크릿키 값
     * @param {string} ext 확장자
     * @returns {List} 추출 text list
     */
    public MemberResDto callApi(String type, String filePath, String naver_secretKey, String ext) {

        String apiURL = url;
        String secretKey = naver_secretKey;
        String imageFile = filePath;
        List<String> parseData = null;

        log.info("callApi Start!");

        MemberResDto memberRes = null;
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod(type);
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey); // 시크릿 키를 설정합니다.

            JSONObject json = new JSONObject(); // JSON 객체 생성
            json.put("version", "V2"); // JSON 객체에 버전 정보 추가
            json.put("requestId", UUID.randomUUID().toString()); // JSON 객체에 요청 ID 추가
            json.put("timestamp", System.currentTimeMillis()); // JSON 객체에 타임스탬프 추가
            JSONObject image = new JSONObject(); // JSON 객체 생성
            image.put("format", ext); // 이미지 형식 정보 추가
            image.put("name", "demo"); // 이미지 이름 추가
            JSONArray images = new JSONArray(); // JSON 배열 생성
            images.add(image); // JSON 배열에 이미지 정보 추가
            json.put("images", images); // JSON 객체에 이미지 배열 추가
            String postParams = json.toString(); // JSON 객체를 문자열로 변환

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();


            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // 데이터 리스트로 가공
            parseData = jsonparse(response);

            // 가공한 데이터 dto 변환
            memberRes = MemberResDto.builder()
                    .studentNum(parseData.get(0))
                    .username(parseData.get(1))
                    .major(parseData.get(2))
                    .build();


        } catch (Exception e) {
            System.out.println(e);
        }
        return memberRes;
    }


    /**
     * writeMultiPart
     * @param {OutputStream} out 데이터를 출력
     * @param {string} jsonMessage 요청 params
     * @param {File} file 요청 파일
     * @param {String} boundary 경계
     */
    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
    /**
     * 데이터 가공
     * @param {StringBuffer} response 응답값
     * @returns {List} result text list
     */
    private static List<String> jsonparse(StringBuffer response) throws ParseException {
        // json 파싱
        JSONParser jp = new JSONParser();
        JSONObject jobj = (JSONObject) jp.parse(response.toString());
        // images 배열 객체로 변환
        JSONArray JSONArrayPerson = (JSONArray)jobj.get("images");
        JSONObject JSONObjImage = (JSONObject)JSONArrayPerson.get(0);
        JSONArray s = (JSONArray) JSONObjImage.get("fields");
        //
        List<Map<String, Object>> m = JsonUtil.getListMapFromJsonArray(s);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> as : m) {
            result.add((String) as.get("inferText"));
        }

        return result;
    }
}
