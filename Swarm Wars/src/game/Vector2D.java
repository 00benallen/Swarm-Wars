package game;
public class Vector2D {
	private float x, y;
	
	public Vector2D(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Vector2D() {
		setX(0);
		setY(0);
	}
	
	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}
	
	public Vector2D setToZeroVector(Vector2D vector) {
		vector = new Vector2D();
		return vector;
	}
	
	public static Vector2D negateVector(Vector2D vector) {
		vector.setX(vector.getX()*-1);
		vector.setY(vector.getY()*-1);
		return vector;
	}
	
	public static float calculateMagnitude(Vector2D vector) {
		double magnitude = Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
		return (float)magnitude;
	}
	
	public static Vector2D multiplyByScalar(Vector2D vector, float scalar) {
		vector.setX(vector.getX()*scalar);
		vector.setY(vector.getY()*scalar);
		return vector;
	}
	
	public static Vector2D divideByScalar(Vector2D vector, float scalar) {
		vector.setX(vector.getX()/scalar);
		vector.setY(vector.getY()/scalar);
		return vector;
	}
	
	public static Vector2D normalizeVector(Vector2D vector) {
		float magnitude = calculateMagnitude(vector);
		vector.setX(vector.getX()/magnitude);
		vector.setY(vector.getY()/magnitude);
		return vector;
	}
	
	public static Vector2D addVectorToVector(Vector2D vector1, Vector2D vector2) {
		vector1.setX(vector1.getX() + vector2.getX());
		vector1.setY(vector1.getY() + vector2.getY());
		return vector1;
	}
	
	public static Vector2D subtractVectorFromVector(Vector2D vector1, Vector2D vector2) {
		vector1.setX(vector1.getX() - vector2.getX());
		vector1.setY(vector1.getY() - vector2.getY());
		return vector1;
	}
	
	public static float computeDistanceBetweenVectors(Vector2D vector1, Vector2D vector2) {
		Vector2D vectorBetween = new Vector2D(vector2.getX() - vector1.getX(), vector2.getY() - vector1.getY());
		float distance = calculateMagnitude(vectorBetween);
		return distance;
	}
	
	public static float computeDotProductOfVectors(Vector2D vector1, Vector2D vector2) {
		float dotProduct = vector1.getX()*vector2.getX() + vector1.getY()*vector2.getY();
		return dotProduct;
	}
	
	public static boolean checkVectorEquality(Vector2D vector1, Vector2D vector2) {
		if(vector1.getX() == vector2.getX() && vector1.getY() == vector2.getY()) {
			return true;
		}
		else
			return false;
	}
	
	public float getX() {return this.x;}
	public float getY() {return this.y;}
	
	public String toString() {
		String vectorString = "x: " + this.x + " y: " + this.y;
		return vectorString;
	}

}

