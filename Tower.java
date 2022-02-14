import java.util.ArrayList;

public class Tower {
    public int score;
    public int height;
    public ArrayList<TowerPiece> tower = new ArrayList<>();
    public ArrayList<TowerPiece> out = new ArrayList<>();
    public Tower (ArrayList<TowerPiece> input){
        tower.addAll(input);
        height = input.size();
        score = calcScore();
    }
    public int calcScore (){
        int totCost = 0;
        if(!tower.get(0).type.equals("Door")){
            return 0;
        }
        if(!tower.get(height-1).type.equals("Lookout")){
            return 0;
        }
        for(int i = 1; i < tower.size()-1; i++){
            if(!(tower.get(i).type.equals("Wall") || tower.get(i).type.equals("Wall "))){
                return 0;
            }
            if(tower.get(i).strength < tower.size()-(i+1)){		//TODO double check this?
                return 0;
            }
            if(tower.get(i).width > tower.get(i-1).width){		//TODO double check this?
                return 0;
            }
            else{
                totCost += tower.get(i).cost;
            }
        }
        return (10 + height*height) - totCost;
    }
    public void takeOut(TowerPiece p)
    {
    	tower.remove(p);
    	out.add(p);
    }
    public void putIn(TowerPiece p)
    {
    	out.remove(p);
    	tower.add(p);
    }
    public void printout()
    {
    	for(TowerPiece p : tower)
    	{
	    	  System.out.print(p.type + "\t");
	    	  System.out.print(p.width + "\t");
	    	  System.out.print(p.strength + "\t");
	    	  System.out.print(p.cost + "\n");
    	}
    }
}
