import java.util.ArrayList;

public class tower {
    public int score;
    public int height;
    public ArrayList<TowerPiece> tower = new ArrayList<>();
    public tower (ArrayList<TowerPiece> input){
        tower = input;
        height = input.size();
        score = calcScore();
    }
    public int calcScore (){
        int totCost = 0;
        if(tower.get(0).type != "Door"){
            return 0;
        }
        if(tower.get(height-1).type != "Lookout"){
            return 0;
        }
        for(int i = 1; i < tower.size(); i++){
            if(tower.get(i).type != "Wall"){
                return 0;
            }
            if(tower.get(i).strength < tower.size()-i-1){
                return 0;
            }
            if(tower.get(i).width > tower.get(i-1).width){
                return 0;
            }
            else{
                totCost += tower.get(i).cost;
            }
        }
        return (10 + height*height) - totCost;
    }
}
