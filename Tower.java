import java.util.ArrayList;
import java.util.Random;

public class Tower {
    public int score;
    public int height;
    public ArrayList<TowerPiece> tower = new ArrayList<>();
    public ArrayList<TowerPiece> out = new ArrayList<>();
    public Tower (ArrayList<TowerPiece> input, ArrayList<TowerPiece> out){
        tower.addAll(input);
        this.out.addAll(out);
        height = input.size();
        score = calcScore();
    }
    public Tower(Tower t2)
    {
    	tower.addAll(t2.tower);
    	this.out = t2.out;
        height = t2.height;
        score = calcScore();
    }
    public int calcScore (){
    	height = tower.size();
        int totCost = 0;
        if (tower.isEmpty())
        {
        	return 0;
        }
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
    public void putRandom()
    {
    	if (out.isEmpty())
    		return;
    	else
    	{
    		int rand = new Random().nextInt(out.size());
			TowerPiece p = out.remove(rand);
			//int rand2 = new Random().nextInt(tower.size());
			tower.add(p);
    	}
    }
    public void pullRandom()
    {
    	if (tower.isEmpty())
    		return;
    	else
    	{
    		int rand = new Random().nextInt(tower.size());
			TowerPiece p = tower.remove(rand);
			if (out.isEmpty())
				out.add(p);
			else
			{
				int rand2 = new Random().nextInt(out.size() + 1);
				out.add(rand2, p);
			}
    	}
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
