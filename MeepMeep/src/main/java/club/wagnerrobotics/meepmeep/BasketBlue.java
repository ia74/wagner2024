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
    public Pose2d startPosition() {
        return new Pose2d(35.5, 61.5, Math.toRadians(-90));
    }
    public static void score() {
    }
    public static void grabFromBelow() {

    }
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 30)
                .build();



        Pose2d basketPosition = new Pose2d(52, 53, Math.toRadians(45));
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35.5, 61.5, Math.toRadians(-90)))
                .lineToY(55)
                .splineToLinearHeading(basketPosition, 0.0)
                .waitSeconds(1)
                        .strafeToLinearHeading(new Vector2d( -60, 58), Math.toRadians(0))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}