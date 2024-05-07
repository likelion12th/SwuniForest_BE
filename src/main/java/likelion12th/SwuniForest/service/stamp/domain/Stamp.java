package likelion12th.SwuniForest.service.stamp.domain;

import jakarta.persistence.*;
import likelion12th.SwuniForest.service.member.domain.Member;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Member member;
    @Builder.Default
    private boolean dep1Checked = false;
    @Builder.Default
    private boolean dep2Checked = false;
    @Builder.Default
    private boolean dep3Checked = false;
    @Builder.Default
    private boolean dep4Checked = false;
    @Builder.Default
    private boolean dep5Checked = false;
    @Builder.Default
    private boolean dep5hecked = false;
    @Builder.Default
    private boolean dep6Checked = false;
    @Builder.Default
    private boolean dep7Checked = false;
    @Builder.Default
    private boolean dep8Checked = false;
    @Builder.Default
    private boolean dep9Checked = false;
    @Builder.Default
    private boolean dep10Checked = false;
    @Builder.Default
    private boolean dep11Checked = false;
    @Builder.Default
    private boolean dep12Checked = false;
    @Builder.Default
    private boolean dep13Checked = false;
    @Builder.Default
    private boolean dep14Checked = false;
    @Builder.Default
    private boolean dep15Checked = false;
    @Builder.Default
    private boolean dep16Checked = false;
    @Builder.Default
    private boolean dep17Checked = false;

    @Builder.Default
    private boolean dep18Checked = false;
    @Builder.Default
    private boolean dep19Checked = false;
    @Builder.Default
    private boolean dep20Checked = false;
    @Builder.Default
    private boolean dep21Checked = false;
    @Builder.Default
    private boolean dep22Checked = false;
    @Builder.Default
    private boolean dep23Checked = false;
    @Builder.Default
    private boolean dep24Checked = false;
    @Builder.Default
    private boolean dep25Checked = false;
    @Builder.Default
    private boolean dep26Checked = false;
    @Builder.Default
    private boolean dep27Checked = false;
    @Builder.Default
    private boolean dep28Checked = false;
    @Builder.Default
    private boolean dep29Checked = false;
    @Builder.Default
    private boolean dep30Checked = false;
    @Builder.Default
    private boolean dep31Checked = false;
    @Builder.Default
    private boolean dep32Checked = false;
    @Builder.Default
    private boolean dep33Checked = false;
    @Builder.Default
    private boolean dep34Checked = false;
    @Builder.Default
    private boolean dep35Checked = false;



}