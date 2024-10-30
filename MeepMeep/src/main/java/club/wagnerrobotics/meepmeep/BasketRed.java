package club.wagnerrobotics.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BasketRed {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(36, 60, Math.toRadians(-90)))
                .turnTo(Math.toRadians(0))
                .strafeTo(new Vector2d(55, 60))
                .waitSeconds(4) //open claw to drop element
                .lineToX(-48)
                .turnTo(Math.toRadians(270))
                .lineToY(22)
                .waitSeconds(4) // close claw
                .lineToY(60)
                .turnTo(Math.toRadians(0))
                .lineToX(55)
                .waitSeconds(4) //open claw to drop element
                .lineToX(-55)

//                .waitSeconds(4) // Make the claw open, then down
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}