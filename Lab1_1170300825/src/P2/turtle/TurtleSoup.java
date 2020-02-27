/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.function.LongToDoubleFunction;

import org.junit.experimental.theories.FromDataPoints;

import static org.junit.Assert.assertNotNull;

import java.beans.ParameterDescriptor;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
	
    public static void drawSquare(Turtle turtle, int sideLength) {
    	drawRegularPolygon(turtle,4,sideLength);
//        throw new RuntimeException("implement me!");
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
//    	内角和度数为((sides-2)*180，然后一共有sides个角
    	return ((double) ((sides - 2) * 180)) / sides;
//        throw new RuntimeException("implement me!");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
//    	外角和为360度
    	return Math.round(360 / (180 - (float) angle));
//        throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
//    	画一个规则多边形
//    	首先根据边数求出外角
    	double rotation = 180 - calculateRegularPolygonAngle(sides);
//    	使用旋转turn与前进forward绘制多边形
        for(int i = 0; i < sides; i++){
            turtle.forward(sideLength);
            turtle.turn(rotation);
        }
//        throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
//    	计算当前点、当前方 turn到 目标点需要的度数
    	int dx = targetX - currentX;
        int dy = targetY - currentY;
//		计算两点连线偏移north的角度（arctan(tan)）
        double angleFN = Math.toDegrees(Math.atan2(dx, dy));
        double angle = angleFN - currentBearing;
//		保证角度的非负
        if(angle < 0)
            angle += 360;
        return angle;
//        throw new RuntimeException("implement me!");
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	List<Double> ansDoubles = new ArrayList<>();
    	Double currentBearing = 0.0;
    	int len=xCoords.size();
    	for(int i=1;i<len;i++) {
    		Double angle = calculateBearingToPoint(currentBearing, xCoords.get(i-1), yCoords.get(i-1),xCoords.get(i) , yCoords.get(i));
    		ansDoubles.add(angle);
    		currentBearing+=angle;
    	}
    	return ansDoubles;
//        throw new RuntimeException("implement me!");
    }
    
    public static final Double eps= 1e-8;
//    计算vec<a1,a2> vec<b1,b2>两向量夹角之间的cos值
    public static Double getCosOf2Vector(Point a1,Point a2,Point b1,Point b2) {
    	Point vec1 = new Point(a2.x()-a1.x(),a2.y()-a1.y());
    	Point vec2 = new Point(b2.x()-b1.x(),b2.y()-b1.y());
    	Double absVec1 = Math.sqrt(vec1.x()*vec1.x()+vec1.y()*vec1.y());
    	Double absVec2 = Math.sqrt(vec2.x()*vec2.x()+vec2.y()*vec2.y());
    	return (vec1.x()*vec2.x()+vec1.y()*vec2.y())/(absVec1*absVec2);
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
		// 暴力算法

		if (points.size() <= 3)
			return points;
		System.out.println("SET");
		for(Point pt:points) {
			System.out.println(pt.x()+" "+pt.y());
		}
		System.out.println();
		Set<Point> ansSet = new HashSet<>();// 记录返回点
		ansSet.clear();
		double x1=0, y1=0, x2=0, y2=0, x=0, y=0,temp1,temp2;
		int flag = 1;
		int flag1 =0;
		
		for (Point ptPoint3 : points) {
			x1 = ptPoint3.x();
			y1 = ptPoint3.y();
			
			for (Point ptPoint1 : points) {
				flag=1;
				flag1=0;
				x2 = ptPoint1.x();
				y2 = ptPoint1.y();
				
				if ((x1 == x2) && (y1 == y2)) {
//					修改了这里 如果这种情况跳出循环的话flag就会是1 所以出错
					flag=0;
					continue;
				}
				
//				我就只改了这个地方 把temp初始化移到了这里
				temp1 = 0;
				temp2 = 0;
				for (Point ptPoint2 : points) {

					x = ptPoint2.x();
					y = ptPoint2.y();
					if ((x == x2) && (y == y2)) {
						continue;
					}
					if((x==x1)&&(y==y1))
					{
						continue;
					}
					
					if (temp1 == 0) {
						temp1 = (y1 - y2) * x + (x2 - x1) * y + x1 * y2 - x2 * y1;
						
					}
					else {
						temp2 = (y1 - y2) * x + (x2 - x1) * y + x1 * y2 - x2 * y1;
						if (temp1 * temp2 < 0) {
							flag = 0;
							break;
						}
					}
					
					double A=0,B=0,C=0;
					if((y1 - y2) * x + (x2 - x1) * y + x1 * y2 - x2 * y1 == 0)
					{
						A=Distance(x1, y1, x2, y2);
						B=Distance(x1, y1, x, y);
						C=Distance(x, y, x2, y2);
						if(C>A&&C>B)
						{
							flag=0;
							flag1=1;
							break;
						}
					}
				}
				if(flag==1||flag1==1)
				{
					break;
				}

			}
			if (flag == 1) {

				ansSet.add(ptPoint3);
			}

		}
		
		for(Point pt:ansSet) {
			System.out.println(pt.x()+" "+pt.y());
		}
		System.out.println();
		return ansSet;
    }
    public static double Distance(double x,double y,double a,double b) {
    	return (x-a)*(x-a)+(y-b)*(y-b);
    }

	public static Double caculateDistance(Point p, Point a) {
//		Double kDouble = (a.y() - b.y()) / (a.x() - b.x());
//		Double bDouble = a.y() - kDouble * a.x();
//		return Math.abs(p.x() * kDouble + bDouble) / Math.sqrt(kDouble * kDouble + bDouble * bDouble);
		return (p.x()-a.x())*(p.x()-a.x())+(p.y()-a.y())*(p.y()-a.y());
	}
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	turtle.color(PenColor.PINK);
    	for(int i = 0; i < 15; i++){ // i < 15 to limit size of shapes
            drawRegularPolygon(turtle, i, i*10);
            drawRegularPolygon(turtle, i*10, i);
        }
//        throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
