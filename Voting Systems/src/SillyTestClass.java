
public class SillyTestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ballot ballot0 = new Ballot("elections/election0/ballot0");
		//System.out.println(ballot0.get(0));
		
		/**
		for( String line : ballot0.vote_storage) {
			if (line.equals("--")) {
				break;
			}
			System.out.println(line);
		}
		**/
		
		for(int i=0; i<ballot0.size(); i++) {
			String line = ballot0.get(i);
			if( line.equals("--") ){
				break;
			}
			System.out.println(line);
		}
		
	}

}
