
package personal;

import robocode.*;
import java.awt.*;

public class robot1 extends AdvancedRobot {

	public void set_angle(double angle) {
		angle = getHeading() - angle;
		if (angle > 180) {
			if (angle < 0) {
				angle = angle * -1;
			}
			angle -= 360;
		}
		turnRight(angle * -1);
	}

	public void go_to_coord(double x, double y) {
		double ax, ay, angle, amount_move;
		ax = x - getX();
		ay = y - getY();
		if (ay != 0 && ax != 0) {
			angle = Math.atan(Math.abs(ax) / Math.abs(ay));
			angle = Math.toDegrees(angle);
		} else if (ay > getY() && ax == 0)
			angle = 0;
		else if (ay < getY() && ax == 0)
			angle = 180;
		else if (ax > getX() && ay == 0)
			angle = 90;
		else
			angle = 270;

		if (ax < 0 && ay < 0)
			angle += 180;
		else if (ax < 0 && ay > 0)
			angle = 360 - angle;
		else if (ax > 0 && ay < 0)
			angle = 180 - angle;
		set_angle(angle);
		amount_move = Math.sqrt(Math.pow(ax, 2) + Math.pow(ay, 2));
		ahead(amount_move);
	}

	public void run() {
		// Set colors
		setBodyColor(Color.black);
		setGunColor(Color.red);
		setRadarColor(Color.black);
		setScanColor(Color.orange);
		go_to_coord(getX(), 50);
		set_angle(90);
		turnGunLeft(90);
		while (true) {
			go_to_coord(getBattleFieldWidth() - 50, getY());
			go_to_coord(getX(), getBattleFieldHeight() - 50);
			go_to_coord(50, getY());
			go_to_coord(getX(), 50);
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		super.onScannedRobot(event);
		fire(2);
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		double bearing = event.getBearing();
		turnGunLeft(bearing);
		fire(3);
		turnGunRight(bearing);
	}

}
