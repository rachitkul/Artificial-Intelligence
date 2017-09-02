import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;



class GamePosition
{
	public int points;
	public char player;
}

public class homework {
	
	public static File file;
	public static PrintWriter printWriter;
	public static int N;
	public static String mode;
	public static char playerMe;
	public static char playerOpponent;
	public static int depth;
	public static int d;
	public static int resultColumn;
	public static int resultRow;
	public static String Action;
	public GamePosition[][] gamePosition;
	public static GamePosition[][] outputGamePosition;
	
	public int Utility(GamePosition[][] gP)
	{
		int score_me=0,score_opponent=0;
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				if(gP[i][j].player == playerMe)
				{
					score_me=score_me+gP[i][j].points;
				}				
				else if(gP[i][j].player == '.')
				{
					continue;
				}
				else
				{
					score_opponent=score_opponent+gP[i][j].points;
				}
						
			}
		}
		return (score_me-score_opponent);
	}
	
	//////////////////////////////////////Start of MINIMAX ************************************************************
	public void minimax()
	{
		maxValue(gamePosition);
		
		//Output Printing
		int a=resultColumn+64;
		System.out.println(a);
		char Column=(char)a;
		printWriter.println(Column+""+resultRow+" "+Action);
		
		System.out.println(Column);
		System.out.println(resultRow);
		System.out.println(Action);
		
		for(int i=0;i<N;i++)			//Reading current positions of pieces
		{
			for(int j=0;j<N;j++)
			{
				printWriter.print(outputGamePosition[i][j].player);
			}
			printWriter.println();
		}
		System.out.println("Done");
		return;
		
	}
	
	public int maxValue(GamePosition[][] gP)
	{
		d++;
		int gameOverFlag=0;
		int flag=0;
		int alpha;
		if (d==depth)
		{
			d--;
			return Utility(gP);
		}
		alpha=-999999999;
		
			
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						gameOverFlag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						
						gP1[i][j].player=playerMe;
						
						int temp=minValue(gP1);
						
						if(temp>alpha)
						{
							flag=0;
							alpha=temp;
							if(d==0)
							{
								resultRow=i+1;
								resultColumn=j+1;
								Action="Stake";
								outputGamePosition=gP1;
								
							}
						}
						if(d==0 && temp==alpha && flag==1)
						{
							flag=0;
							//alpha=temp;
							
							
								resultRow=i+1;
								resultColumn=j+1;
								Action="Stake";
								outputGamePosition=gP1;
								
							
						}
												
						//Raid checking
						//gP1=gP;							//Logical Error
						//gP1[i][j].player=playerMe;
						int a=i-1;
						int b=j-1;
						int c=i+1;
						int e=j+1;
						
						if((a>=0 && gP1[a][j].player== playerMe) || (c<N && gP1[c][j].player==playerMe) || (b>=0 && gP1[i][b].player==playerMe) || (e<N && gP1[i][e].player==playerMe))
						{
							if(a>=0)
							{
								if(gP1[a][j].player == playerOpponent)
								{
									gP1[a][j].player=playerMe;
								}
							}
							if(c<N)
							{
								if(gP1[c][j].player == playerOpponent)
								{
									gP1[c][j].player=playerMe;
								}
							}
							if(b>=0)
							{
								if(gP1[i][b].player == playerOpponent)
								{
									gP1[i][b].player=playerMe;
								}
							}
							if(e<N)
							{
								if(gP1[i][e].player == playerOpponent)
								{
									gP1[i][e].player=playerMe;
								}//
							}
							
							int temp1=minValue(gP1);
							
							if(temp1>alpha)
							{
								flag=1;
								alpha=temp1;
								if(d==0)
								{
									resultRow=i+1;
									resultColumn=j+1;
									Action="Raid";
									outputGamePosition=gP1;
								}
							}
						}
					}
					
				}
				
			}
			
			if(gameOverFlag==0)
			{
				d--;
				return Utility(gP);
			}
			
			d--;
			return alpha;
			
		
		
	}
	
	
	
	public int minValue(GamePosition[][] gP)
	{
		d++;
		int beta;
		int gameOverFlag=0;
		
		if (d==depth)
		{
			d--;
			return Utility(gP);
		}
		beta=999999999;
		
			
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						gameOverFlag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						gP1[i][j].player=playerOpponent;
						
						int temp=maxValue(gP1);
						if(temp<beta)
						{
							
							beta=temp;
						}
						
						int a=i-1;
						int b=j-1;
						int c=i+1;
						int e=j+1;
						
						if((a>=0 && gP1[a][j].player== playerOpponent) || (c<N && gP1[c][j].player==playerOpponent) || (b>=0 && gP1[i][b].player==playerOpponent) || (e<N && gP1[i][e].player==playerOpponent))
						{
							if(a>=0)
							{
								if(gP1[a][j].player == playerMe)
								{
									gP1[a][j].player=playerOpponent;
								}
							}
							if(c<N)
							{
								if(gP1[c][j].player == playerMe)
								{
									gP1[c][j].player=playerOpponent;
								}
							}
							if(b>=0)
							{
								if(gP1[i][b].player == playerMe)
								{
									gP1[i][b].player=playerOpponent;
								}
							}
							if(e<N)
							{
								if(gP1[i][e].player == playerMe)
								{
									gP1[i][e].player=playerOpponent;
								}
							}
							
							int temp1=maxValue(gP1);
							if(temp1<beta)
							{
								beta=temp1;
							}
						}
						
					}
					
				}
				
			}
			
			if(gameOverFlag==0)
			{
				d--;
				return Utility(gP);
			}
			d--;
			return beta;
	}
	////////////////////////////////////////End of MINIMAX************************************************
	
	
	
	////////////////////////////////////////Start of Alpha Beta*******************************************
	public void alphabeta()
	{
		int alpha=-999999999;
		int beta=999999999;
		prun_maxValue(gamePosition,alpha,beta);
		
		//Output Printing
		int a=resultColumn+64;
		System.out.println(a);
		char Column=(char)a;
		printWriter.println(Column+""+resultRow+" "+Action);
		System.out.println(resultColumn);
		System.out.println(Column);
		System.out.println(resultRow);
		System.out.println(Action);
		
		for(int i=0;i<N;i++)			//Reading current positions of pieces
		{
			for(int j=0;j<N;j++)
			{
				printWriter.print(outputGamePosition[i][j].player);
			}
			printWriter.println();
		}
		System.out.println("Done");
		return;
		
	}
	
	public int prun_maxValue(GamePosition[][] gP,int alpha,int beta)
	{
		d++;
		//int flag=0;
		int prun_flag=0;
		
		if (d==depth)
		{
			d--;
			return Utility(gP);
		}
				
			
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						prun_flag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						
						gP1[i][j].player=playerMe;
						//int temp=Utility(gP);
						int temp=prun_minValue(gP1,alpha,beta);
						
						if(temp>alpha)
						{
							//flag=0;
							alpha=temp;
							if(d==0)
							{
								resultRow=i+1;
								resultColumn=j+1;
								Action="Stake";
								outputGamePosition=gP1;
								if(resultColumn==5 && resultRow==1)
								System.out.println("Temp:"+temp);
							}
							if(beta<=alpha)
							{
								d--;
								//System.out.println("Alpha-beta Pruning........................");
								return beta;
							}
						}
					}
				}
			}
						
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						prun_flag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						
						gP1[i][j].player=playerMe;			
						int a=i-1;
						int b=j-1;
						int c=i+1;
						int e=j+1;
						
						if((a>=0 && gP1[a][j].player== playerMe) || (c<N && gP1[c][j].player==playerMe) || (b>=0 && gP1[i][b].player==playerMe) || (e<N && gP1[i][e].player==playerMe))
						{
							if(a>=0)
							{
								if(gP1[a][j].player == playerOpponent)
								{
									gP1[a][j].player=playerMe;
								}
							}
							if(c<N)
							{
								if(gP1[c][j].player == playerOpponent)
								{
									gP1[c][j].player=playerMe;
								}
							}
							if(b>=0)
							{
								if(gP1[i][b].player == playerOpponent)
								{
									gP1[i][b].player=playerMe;
								}
							}
							if(e<N)
							{
								if(gP1[i][e].player == playerOpponent)
								{
									gP1[i][e].player=playerMe;
								}
							}
							
							int temp1=prun_minValue(gP1,alpha,beta);
							
							if(temp1>alpha)
							{
								//flag=1;
								alpha=temp1;
								if(d==0)
								{
									resultRow=i+1;
									resultColumn=j+1;
									Action="Raid";
									outputGamePosition=gP1;
								}
								if(beta<=alpha)
								{
									d--;
									//System.out.println("Alpha-beta Pruning........................");
									return beta;
								}
							}
						}
					}
					
				}
				
			}
			
			if(prun_flag==0)
			{
				d--;
				return Utility(gP);
			}
			
			d--;
			return alpha;
			
		
		
	}
	
	
	
	public int prun_minValue(GamePosition[][] gP,int alpha,int beta)
	{
		d++;
		int prun_flag=0;
		//int beta;
		if (d==depth)
		{
			d--;
			return Utility(gP);
		}
		//beta=999999999;
		
			
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						prun_flag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						gP1[i][j].player=playerOpponent;
						//int temp=Utility(gP);
						int temp=prun_maxValue(gP1,alpha,beta);
						if(temp<beta)
						{
							beta=temp;
							if(beta<=alpha)
							{
								d--;
								//System.out.println("Alpha-beta Pruning........................");
								return alpha;
							}
						}
					}
				}
			}
												
						//Raid checking
						//gP1=gP;
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(gP[i][j].player == '.')
					{
						//stake
						prun_flag=1;
						GamePosition[][] gP1=new GamePosition[N][N];		//Logical Error
						for(int u=0;u<N;u++)				//Reading the score of each position
						{
							for(int v=0;v<N;v++)
							{
								gP1[u][v]=new GamePosition();
								gP1[u][v].points=gP[u][v].points;
								gP1[u][v].player=gP[u][v].player;
							}
						}
						gP1[i][j].player=playerOpponent;
						int a=i-1;
						int b=j-1;
						int c=i+1;
						int e=j+1;
						
						if((a>=0 && gP1[a][j].player== playerOpponent) || (c<N && gP1[c][j].player==playerOpponent) || (b>=0 && gP1[i][b].player==playerOpponent) || (e<N && gP1[i][e].player==playerOpponent))
						{
							if(a>=0)
							{
								if(gP1[a][j].player == playerMe)
								{
									gP1[a][j].player=playerOpponent;
								}
							}
							if(c<N)
							{
								if(gP1[c][j].player == playerMe)
								{
									gP1[c][j].player=playerOpponent;
								}
							}
							if(b>=0)
							{
								if(gP1[i][b].player == playerMe)
								{
									gP1[i][b].player=playerOpponent;
								}
							}
							if(e<N)
							{
								if(gP1[i][e].player == playerMe)
								{
									gP1[i][e].player=playerOpponent;
								}
							}
							
							int temp1=prun_maxValue(gP1,alpha,beta);
							if(temp1<beta)
							{
								beta=temp1;
								if(beta<=alpha)
								{
									d--;
									//System.out.println("Alpha-beta Pruning........................");
									return alpha;
								}
							}
						}
						
					}
					
				}
				
			}
			if(prun_flag==0)
			{
				d--;
				return Utility(gP);
			}
				
			d--;
			return beta;
	}
	////////////////////////////////////////End of Alpha Beta*********************************************
	
	public static void main(String[] args) {
		
		
		homework hw2=new homework();
		BufferedReader bufferedReader=null;
		file=new File("output.txt");
		
		
		
		//Printing solution into a output file
		
			try 
			{
					file.createNewFile();
					
					
					printWriter= new PrintWriter(file);
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		
		try
		{
			
			bufferedReader = new BufferedReader(new FileReader("input.txt"));
			
		//Reading the file input	
			N=Integer.parseInt(bufferedReader.readLine());
			hw2.gamePosition=new GamePosition[N][N];            ///////Game grid defined
			mode = bufferedReader.readLine();
			char[] ch=new char[2];
			bufferedReader.read(ch, 0, 1);
			bufferedReader.readLine();
			playerMe=ch[0];
			if(playerMe == 'O')
				playerOpponent='X';
			else
				playerOpponent='O';
			depth=Integer.parseInt(bufferedReader.readLine());
				
		//Reading and inserting the problem set in an ArrayList	
			for(int i=0;i<N;i++)				//Reading the score of each position
			{
				
				String[] str=bufferedReader.readLine().split("\\s", N);
				
				for(int j=0;j<N;j++)
				{
					hw2.gamePosition[i][j]=new GamePosition();
					hw2.gamePosition[i][j].points=Integer.parseInt(str[j]);
				}
			}
			for(int i=0;i<N;i++)			//Reading current positions of pieces
			{
				
				
				String str=bufferedReader.readLine();
				 
				for(int j=0;j<N;j++)
				{
					hw2.gamePosition[i][j].player=str.charAt(j);
				}
			}
			 //FileUtils 
			bufferedReader.close();
			
			d=-1;
		
			switch (mode)
			{
				case "MINIMAX": 
								hw2.minimax();
				
								break;
				
				case "ALPHABETA":
								hw2.alphabeta();
								break;
				
				
				
			}
			
		}
		catch(IOException e	)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				bufferedReader.close();
				printWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
