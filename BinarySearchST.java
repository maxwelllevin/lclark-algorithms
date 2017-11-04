public class BinarySearchST<Key extends Comparable<Key>, Value> {

	public int compares;
	private Key[] keys;
	private Value[] vals;
	private int n;

	@SuppressWarnings("unchecked")
	public BinarySearchST(int capacity) {
		keys = (Key[]) new Comparable[capacity];
		vals = (Value[]) new Object[capacity];
		n = 0;
	}

	public int size() {
		return n;
	}

	public Value get(Key key) {
		if (isEmpty())
			return null;
		int i = rank(key);
		compares++;
		if (i < n && keys[i].compareTo(key) == 0)
			return vals[i];
		else
			return null;
	}

	private boolean isEmpty() {
		return n == 0;
	}

	private int rank(Key key) {
		int lo = 0, hi = n - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			compares++;
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	public void put(Key key, Value val) {
		compares = 0;
		int i = rank(key);
		if (i < n && keys[i].compareTo(key) == 0) {
			vals[i] = val;
			return;
		}
		compares++;
		for (int j = n; j > i; j--) {
			keys[j] = keys[j - 1];
			vals[j] = vals[j - 1];
		}
		keys[i] = key;
		vals[i] = val;
		n++;
	}

	public void lazyDelete(Key key) {
		int i = rank(key);
		vals[i] = null;
	}

	public void printAll() {
		StdOut.println("\nPrinting All Symbol Table Values in Order:");
		for (int i = 0; i < size(); i++)
			StdOut.println(vals[i]);
		StdOut.printf("Number of compares:" + compares + "\n");
	}

	public static void main(String[] args) {
		BinarySearchST<Character, Character> easyquestion = new BinarySearchST<Character, Character>(12);
		easyquestion.put('E', 'E');
		easyquestion.printAll();
		easyquestion.put('A', 'A');
		easyquestion.printAll();
		easyquestion.put('S', 'S');
		easyquestion.printAll();
		easyquestion.put('Y', 'Y');
		easyquestion.printAll();
		easyquestion.put('Q', 'Q');
		easyquestion.printAll();
		easyquestion.put('U', 'U');
		easyquestion.printAll();
		easyquestion.put('E', 'E');
		easyquestion.printAll();
		easyquestion.put('S', 'S');
		easyquestion.printAll();
		easyquestion.put('T', 'T');
		easyquestion.printAll();
		easyquestion.put('I', 'I');
		easyquestion.printAll();
		easyquestion.put('O', 'O');
		easyquestion.printAll();
		easyquestion.put('N', 'N');
		easyquestion.printAll();
	}

}