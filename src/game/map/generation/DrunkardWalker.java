package game.map.generation;

import java.util.Random;

public class DrunkardWalker {

    public int[][] DrunkardWalk(int SizeX, int SizeY, int numFloorTiles)
    {
        int[][] tiles = new int[SizeX][SizeY];

        //fill it with 1s
        for(int i=0; i < SizeX; ++i)
        {
            for(int j=0; j < SizeY; ++j)
            {
                tiles[i][j] = 1;
            }
        }

        int currX = new Random().nextInt(SizeX);
        int currY = new Random().nextInt(SizeY);
        int numOfFloorTiles = numFloorTiles;

        int currentFloorTiles = 0;
        while(currentFloorTiles < numOfFloorTiles) {

            //if not already floor, make it a floor
            if (tiles[currX][currY] != 0)
            {
                tiles[currX][currY] = 0;
                ++currentFloorTiles;
            }

            //move drunkard random direction
            switch (new Random().nextInt(4)) {
                case 0:
                    --currY;
                    break;
                case 1:
                    ++currX;
                    break;
                case 2:
                    ++currY;
                    break;
                case 3:
                    --currX;
                    break;
            }

            //bound it
            if (currY < 1) {
                currY = 1;
            }
            if (currY >= SizeY - 1) {
                currY = SizeY - 2;
            }
            if (currX < 1) {
                currX = 1;
            }
            if (currX >= SizeX - 1) {
                currX = SizeX - 2;
            }

        }

        //set the other tiles to be empty
        for(int i=0; i < SizeX; ++i)
        {
            for(int j=0; j < SizeY; ++j)
            {
                boolean IsUpNotFloor;
                boolean IsDownNotFloor;
                boolean IsLeftNotFloor;
                boolean IsRightNotFloor;
                try {
                    IsLeftNotFloor = tiles[i-1][j] != 0;
                } catch (Exception e) {
                    IsLeftNotFloor = true;
                }
                try {
                    IsRightNotFloor = tiles[i+1][j] != 0;
                } catch (Exception e) {
                    IsRightNotFloor = true;
                }
                try {
                    IsUpNotFloor = tiles[i][j-1] != 0;
                } catch (Exception e) {
                    IsUpNotFloor = true;
                }
                try {
                    IsDownNotFloor = tiles[i][j+1] != 0;
                } catch (Exception e) {
                    IsDownNotFloor = true;
                }

                if(IsRightNotFloor && IsLeftNotFloor && IsUpNotFloor && IsDownNotFloor)
                {
                    tiles[i][j] = 2;
                }

            }
        }

        return tiles;
    }


}
