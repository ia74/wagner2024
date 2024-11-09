package club.wagnerrobotics.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BasketBlue {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 30)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(36, 60, Math.toRadians(-90)))
                .lineToY(55)
                .splineToLinearHeading(new Pose2d(56, 52, Math.toRadians(45)), 0.0)
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-57.5, 12), Math.toRadians(270))

                .waitSeconds(1)
                .turnTo(Math.toRadians(45))
                        .lineToY(53)
                .strafeTo(new Vector2d(54, 53))
                .waitSeconds(1)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}