package com.example.androidapp2;

public class Player extends CONSTANT {

	
	public int _i;
	public int _j;
	
	public int get_j() {
		return _j;
	}
	public void set_j(int _j) {
		this._j = _j;
	}
	public int get_i() {
		return _i;
	}
	public void set_i(int _i) {
		this._i = _i;
	}
	//private int [][] map;
	
	Player(int[][] map){		
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++)
			{
				if(map[i][j]==4)
				{
					_i=i;
					_j=j;
				}
			}
		map[_i][_j]=0;
		
	}
	public void seDeplacer(int[][] map, char toucheAppuye){				
		switch(toucheAppuye)
		{
			case 'd':
				seDeplacerVersDroite(map);
				break;
			case'q':
				SeDeplacerVersGauche(map);
								
				break;
			case's':
					SeDeplacerVersBas(map);
						
						
			break;
			case 'z':
				SeDeplacerVersHaut(map);	
				break;
			default:
				System.out.println("veuillez utilisez les touches 'd','q','z','s' pour deplacer le player 'r' pour recommencer le niveau en cour et 'p' pour quitter");
							
		}			
		
	}
	
		
	private void seDeplacerVersDroite(int[][] map)
	{
		if(_j==map[0].length-1||(_j==map[0].length-2 && (map[_i][_j+1]==BOX||map[_i][_j+1]==BOX_OK)))
			return;			//pour ne pas depassez les limites du plateau
		if(map[_i][_j+1]==BLANK||map[_i][_j+1]==STORAGE_LOCATION)
			_j++;
		else if(map[_i][_j+1]==BOX )
		{
			if(map[_i][_j+2]==STORAGE_LOCATION )			
				map[_i][_j+2]=BOX_OK;						
			else if(map[_i][_j+2]==BLANK)												
				map[_i][_j+2]=BOX; 		
			else
				return;	//lorsqu'il y'a un mur derriere la caisse ou une autre caisse on ne bouge pas
			
			map[_i][_j+1]=BLANK;
			_j++;	
		}
		else if(map[_i][_j+1]==BOX_OK)
		{
			if(map[_i][_j+2]==STORAGE_LOCATION )			
				map[_i][_j+2]=BOX_OK;						
			else if(map[_i][_j+2]==BLANK)												
				map[_i][_j+2]=BOX; 		
			else
				return;
			
			map[_i][_j+1]=STORAGE_LOCATION;
			_j++;	
		}
				
		
	}
	private void SeDeplacerVersGauche(int[][] map) 
	{
		if(_j==0||(_j==1 && (map[_i][_j-1]==BOX||map[_i][_j-1]==BOX_OK)))
			return;
			if((map[_i][_j-1]==BLANK||map[_i][_j-1]==STORAGE_LOCATION)&&_j!=0)
				_j--;
			else if(map[_i][_j-1]==BOX)
			{
				if(map[_i][_j-2]==STORAGE_LOCATION && _j!=1)			
					map[_i][_j-2]=BOX_OK;						
				else if(map[_i][_j-2]==BLANK)												
					map[_i][_j-2]=BOX; 		
				else
					return;
				
				map[_i][_j-1]=BLANK;
				_j--;	
			}
			else if(map[_i][_j-1]==BOX_OK)
			{
				if(map[_i][_j-2]==STORAGE_LOCATION )			
					map[_i][_j-2]=BOX_OK;						
				else if(map[_i][_j-2]==BLANK)												
					map[_i][_j-2]=BOX; 		
				else
					return;
				
				map[_i][_j-1]=STORAGE_LOCATION;
				_j--;	
			}
		
	}
	private void SeDeplacerVersBas(int[][] map) 
	{
		if(_i==map[0].length-1||(_i==map[0].length-2 && (map[_i+1][_j]==BOX||map[_i+1][_j]==BOX_OK)))
			return;
		if(map[_i+1][_j]==BLANK ||map[_i+1][_j]==STORAGE_LOCATION)
			_i++;
		else if(map[_i+1][_j]==BOX)
		{
			if(map[_i+2][_j]==STORAGE_LOCATION )			
				map[_i+2][_j]=BOX_OK;						
			else if(map[_i+2][_j]==BLANK)												
				map[_i+2][_j]=BOX; 		
			else
				return;						
			map[_i+1][_j]=BLANK;
			_i++;	
		}
		else if(map[_i+1][_j]==BOX_OK)
		{
			if(map[_i+2][_j]==STORAGE_LOCATION )			
				map[_i+2][_j]=BOX_OK;						
			else if(map[_i+2][_j]==BLANK)												
				map[_i+2][_j]=BOX; 		
			else
				return;	
			map[_i+1][_j]=STORAGE_LOCATION;
			_i++;
		}
		
	}
	private void SeDeplacerVersHaut(int[][] map)
	{
		if(_i==0||(_i==1 && (map[_i-1][_j]==BOX||map[_i-1][_j]==BOX_OK)))
			return;
			if(map[_i-1][_j]==BLANK ||map[_i-1][_j]==STORAGE_LOCATION)
				_i--;
			else if(map[_i-1][_j]==BOX)
			{
				if(map[_i-2][_j]==STORAGE_LOCATION )			
					map[_i-2][_j]=BOX_OK;						
				else if(map[_i-2][_j]==BLANK)												
					map[_i-2][_j]=BOX; 		
				else
					return;
				
				map[_i-1][_j]=BLANK;
				_i--;	
			}
			else if(map[_i-1][_j]==BOX_OK)
			{
				if(map[_i-2][_j]==STORAGE_LOCATION )			
					map[_i-2][_j]=BOX_OK;						
				else if(map[_i-2][_j]==BLANK)												
					map[_i-2][_j]=BOX; 		
				else
					return;	
				map[_i-1][_j]=STORAGE_LOCATION;
				_i--;
			}			
		
	}
}
