/**
 * @problem Grid Walking
 * @author ShengMin Zhang
 *
 * @revision 1.0
 * - sort based on the distance to the median, search until first point that's non-decreasing in total time
 */
 
 import java.io.*;
 import java.util.*;
 
 public class Solution {
	class Point {
		private int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}	
		
		public int computeTime(Point p){
			int xdiff = (p.x > x) ? p.x - x : x - p.x;
			int ydiff = (p.y > y) ? p.y - y : y - p.y;
			return (xdiff > ydiff) ? xdiff : ydiff;
		}
		
		@Override
		public String toString(){
			return x + ", " + y;
		}
	}
 
	Point[] xsorted;
	Point[] ysorted;
	int N;
 
	long solve(Point meeting) {
		long d = 0;
		
		// System.out.println("------------------------------");
		
		for(int i = N - 1; i >= 0; i--){
			Point p = xsorted[i];
			if(p == meeting) continue;
			int t = p.computeTime(meeting);
			// System.out.println(t);
			d += t;
		}
		// System.out.println("------------------------------");
		return d;
	}
 
	void run(BufferedReader rd) throws Exception {
		N = Integer.parseInt(rd.readLine());
		xsorted = new Point[N];
		ysorted = new Point[N];
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(rd.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			Point p = new Point(x, y);
			xsorted[i] = p;
			ysorted[i] = p;
		}
		
		Arrays.sort(xsorted, new Comparator<Point>(){
			public int compare(Point a, Point b){
				return a.x - b.x;
			}
		});
		
		Arrays.sort(ysorted, new Comparator<Point>(){
			public int compare(Point a, Point b){
				return a.y - b.y;
			}
		});
	
		int mid = N / 2;
		double xmedian = 0.0;
		double ymedian = 0.0;
		
		if(N % 2 == 0) {
			xmedian = (xsorted[mid].x + xsorted[mid - 1].x) / 2;
			ymedian = (ysorted[mid].y + ysorted[mid - 1].y) / 2;
		} else {
			xmedian = xsorted[mid].x;
			ymedian = ysorted[mid].y;
		}
		
		final double X_MEDIAN = xmedian;
		final double Y_MEDIAN = ymedian;
		
		Arrays.sort(xsorted, new Comparator<Point>(){
			public int compare(Point a, Point b){
				double axdiff = (a.x > X_MEDIAN) ? a.x - X_MEDIAN : X_MEDIAN - a.x;
				double aydiff = (a.y > Y_MEDIAN) ? a.y - Y_MEDIAN : Y_MEDIAN - a.y;
				double adiff = Math.max(axdiff, aydiff);
				
				double bxdiff = (b.x > X_MEDIAN) ? b.x - X_MEDIAN : X_MEDIAN - b.x;
				double bydiff = (b.y > Y_MEDIAN) ? b.y - Y_MEDIAN : Y_MEDIAN - b.y;
				double bdiff = Math.max(bxdiff, bydiff);
				
				if(adiff < bdiff) return -1;
				else if(adiff == bdiff) return 0;
				else return 1;
			}
		});
		
		long min = Long.MAX_VALUE;
		for(Point p: xsorted){
			long d = solve(p);
			if(d <= min) min = d;
			else break;
			// System.out.printf("%s : %d", p.toString(), d);
			// System.out.println();
		}
		System.out.println(min);
	}
 
	public static void main(String[] args) throws Exception {
		Reader rd = (args.length == 0) ? new InputStreamReader(System.in) : new FileReader(args[0]);
		new Solution().run(new BufferedReader(rd));
	}
 }
 