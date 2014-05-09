package eg.edu.guc.loa.ai;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eg.edu.guc.loa.engine.LayoutImpl;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.StandardBoardImpl;
import eg.edu.guc.loa.engine.interfaces.StandardBoard;

public class SimpleMachinePlayer implements MachinePlayer{

	//TODO handle 2 cases: both are connected, there are no available moves
	
	
	private int width;
	private int height;
	private int color = 1;
	private int[][] state;
	private final int inf = 9999999;
	private int depth;
	
	public static void main(String[] args) {
		
		int[][] t = {{0, 0, 0, 1, 0, 0, 1, 0},
					{ 0, 0, 0, 1, 0, 0, 0, 0},
					{ 2, 0, 2, 2, 0, 2, 0, 0},
					{ 2, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 2, 2, 2, 1, 0, 0},
					{ 2, 0, 0, 1, 2, 2, 0, 0},
					{ 0, 0, 0, 0, 0, 2, 0, 0},
					{ 0, 0, 0, 0, 1, 1, 0, 0}};
		
		
		int[][] t1 = 
				{{0, 2, 2, 2, 2, 2, 2, 0},
				{ 1, 0, 0, 1, 0, 0, 0, 1},
				{ 1, 0, 2, 2, 0, 2, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 2, 2, 2, 1, 0, 1},
				{ 1, 0, 0, 1, 2, 2, 0, 1},
				{ 1, 0, 0, 0, 0, 2, 0, 1},
				{ 0, 2, 2, 2, 2, 2, 2, 0}};
		
		int[][] t3 = 
				{{0, 0, 0, 1, 0, 0, 0, 0},
				{ 0, 0, 2, 0, 0, 1, 0, 0},
				{ 0, 0, 1, 1, 2, 2, 0, 0},
				{ 0, 2, 1, 2, 2, 0, 0, 0},
				{ 0, 1, 2, 1, 1, 1, 0, 0},
				{ 0, 0, 0, 1, 2, 0, 0, 0},
				{ 0, 0, 0, 0, 0, 0, 0, 0},
				{ 0, 0, 0, 0, 0, 0, 0, 0}};
		
		StandardBoardImpl board = new StandardBoardImpl(t3);
		SimpleMachinePlayer s = new SimpleMachinePlayer(3);
		s.init(board);
		s.setColor(1);

		Move m = s.getMove();
		System.out.printf("(%d, %d) -> (%d, %d)\n", m.getStart().getRow(), m.getStart().getCol(), m.getEnd().getRow(), m.getEnd().getCol());
		//update(board, s.getMove());
		//print(board);
		
		
			
		
	}
	
	/*
	public static void print(StandardBoardImpl s) {
		int[][] p = s.getBoard();
		for (int[] a : p)
			System.out.println(Arrays.toString(a));
		System.out.println("----------------");
	}*/
	
	public static void print(int[][] p) {
		for (int[] a : p)
			System.out.println(Arrays.toString(a));
		System.out.println("----------------");
	}
	/*
	public static void update(StandardBoardImpl s, Move m) {
		int[][] p = s.getBoard();
		p[m.getEnd().getRow()][m.getEnd().getCol()] = p[m.getStart().getRow()][m.getStart().getCol()];
		p[m.getStart().getRow()][m.getStart().getCol()] = 0;
		
	}*/

	
	private class Node {
		
		public int value;
		public Move move;
		public int[][] state;
		private List<Node> children;
		
		public Node(int[][] s) {
			state = new int[s.length][s[0].length];
			for (int i = 0; i < s.length; i++)
				for (int j = 0; j < s[0].length; j++)
					state[i][j] = s[i][j];
		}
		
		public void move(Move m) {
			move = m;
			state[m.getEnd().getRow()][m.getEnd().getCol()] = state[m.getStart().getRow()][m.getStart().getCol()];
			state[m.getStart().getRow()][m.getStart().getCol()] = 0;
		}
		
		public List<Node> getChildren() {
			if (children == null) {
				children = new LinkedList<Node>();
				List<Move> list = StandardBoardImpl.getAllPossibleMoves(state, color);
				for (Move m : list) {
					Node n = new Node(state);
					n.move(m);
					children.add(n);
				}
			}
			return children;
		}
	}

	public SimpleMachinePlayer(int depth) {
		this.depth = depth;
	}
	
	
	@Override
	public void init(StandardBoard board) {
		this.width 	= board.getLayout().getWidth();
		this.height	= board.getLayout().getHeight();
		state = new int[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				state[i][j] = board.getLayout().getGrid()[i][j].getPieceColor();
		
	}

	@Override
	public void setColor(int color) {
		this.color = color;
		
	}
	
	private boolean equals(int[][] x, int[][] y) {
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[i].length; j++)
				if (x[i][j] != y[i][j])
					return false;
		return true;
	}

	@Override
	public Move getMove() {
		Node n = new Node(state);
		//alphaValue(n, -inf, inf, depth);
		Move res = null;
		int max = -inf;
		for (Node i : n.getChildren()) {
			//print(i.state);
			int value = betaValue(i, -inf, inf, depth);
			Move m = i.move;
			//System.out.printf("%d: (%d, %d) -> (%d, %d)\n", value, m.getStart().getRow(), m.getStart().getCol(), m.getEnd().getRow(), m.getEnd().getCol());
			if (value > max) {
				max = value;
				res = m;
			}
		}
		this.move(res.getStart(), res.getEnd());
		return res;
	}
	
	
	
	private int alphaValue(Node n, int alpha, int beta, int depth) {
		Pair euler = calculateEulerValue(n);
		if (euler.i == 1)
			return inf;
		else if (euler.j == 1)
			return -inf;
		else if (depth == 0)
			return value(n, euler);
		else {
			int v = -inf;
			List<Node> children = n.getChildren();
			for (Node c : children) {
				v = Math.max(v, betaValue(c, alpha, beta, depth - 1));
				if (v >= beta) 
					return v;
				
			}
			alpha = Math.max(v, alpha);
			return v;
		}
	}
	
	private int betaValue(Node n, int alpha, int beta, int depth) {
		Pair euler = calculateEulerValue(n);
		if (euler.i == 1) {
			return inf;
		}
		else if (euler.j == 1) {
			return -inf;
			
		}
		else if (depth == 0)
			return value(n, euler);
		else {
			int v = inf;
			List<Node> children = n.getChildren();
			for (Node c : children) {
				v = Math.min(v, alphaValue(c, alpha, beta, depth - 1));
				if (v <= alpha)
					return v;
				
			}
				beta = Math.min(v, beta);
			return  v;
		}
	}
	
	private int calculateCentralizationHeuristic(Node n) {
		int[][] arr = n.state;
		int res = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] == 0) {
					continue;
				} else if (arr[i][j] == color) {
					res += getWeight(i, j);
				} else {
					res -= getWeight(i, j);
				}
			}
		}
		return res;
	}
	
	private int getWeight(int i, int j) {
		return Math.min(Math.min(i, j), Math.min(height - i - 1, width - j - 1));
	}
	
	
	private void floodFill(int i, int j, int[][] graph, boolean[][] visted) {
		
		LinkedList<Pair> stack = new LinkedList<Pair>();
		int c = graph[i][j];
		stack.push(new Pair(i, j));
		visted[i][j] = true;
		while (!stack.isEmpty()) {
			Pair p = stack.pop();
			for (i = p.i - 1; i <= p.i + 1; i++) {
				for (j = p.j - 1; j <= p.j + 1; j++) {
					if (i < 0 || j < 0 || i >= height || j >= width) {
						continue;
					}
					if (!visted[i][j] && graph[i][j] == c) {
						visted[i][j] = true;
						stack.push(new Pair(i, j));
					}
				}
			}
		}
	}
	
	public Pair calculateEulerValue() {
		return calculateEulerValue(new Node(state));
	}
	
	private Pair calculateEulerValue(Node n) {
		int[][] arr = n.state;
		int x = 0;
		int y = 0;
		boolean[][] visted = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (arr[i][j] == 0) {
					continue;
				}
				if (!visted[i][j] && arr[i][j] == color) {
					x++;
					floodFill(i, j, arr, visted);
				} else if (!visted[i][j]) {
					y++;
					floodFill(i, j, arr, visted);
				}
			}
		}
		return new Pair(x, y);
	}
	
	private int value() {
		Pair euler = calculateEulerValue();
		return calculateCentralizationHeuristic(new Node(state)) - euler.i + euler.j;
	}
	
	private int value(Node n, Pair euler) {
		return calculateCentralizationHeuristic(n) - euler.i + euler.j;
	}

	@Override
	public void move(Point p1, Point p2) {
		state[p2.getRow()][p2.getCol()] = state[p1.getRow()][p1.getCol()];
		state[p1.getRow()][p1.getCol()] = 0;
		
	}
	
}
