
# LiChessDb_to_Tensor
Converts data from my [lichess data to pure fen + eval](https://github.com/VedantJoshi1409/lichess-eval-database-converter) into a form that can easily be turned into tensors in python.

## Usage 
Download the [evaluation data](https://database.lichess.org/#evals) from lichess and run it through my [data to fen + eval](https://github.com/VedantJoshi1409/lichess-eval-database-converter) program. Put it in this program folder and enter the amount of lines you would like to transform, inside of the "main.java" file. Run it and there should be a file named "output.txt" with all the tensor information. 

## Format
Based on [research](https://github.com/official-stockfish/nnue-pytorch/blob/master/docs/nnue.md#feature-set) from Stockfish
![HalfKP[40960]->4x2->8->1](https://github.com/official-stockfish/nnue-pytorch/raw/master/docs/img/HalfKP-40960-4x2-8-1.svg)\
Feature set of the network should be a tensor of size 81920 with values in the tensor set to either 1 or 0. Each index represents a possible combination of (our king square, piece square, piece type, piece owner). The first 40960 inputs consisting of the position from the view of the player to move, and the last 40960 inputs consisting of the position from the view of the enemy, with the board flipped. 


## Example
![](https://i.imgur.com/x7IOFcB.png)
This position can be represented by the following fen
``` 3k4/2r5/8/8/8/1P6/K7/8 w - -, -657```
which gets turned into 
```5628, 42983, 5296, 43301, -657```
where the first 4 numbers are indices where the value should be set to 1, and the last number is the evaluation. 

## How It Works
These numbers are computed by 
- taking our king square, multiplying it by 64 * 5 * 2 (64 possible piece squares, 5 piece types since king not included, 2 options since it is our piece or their piece) 
- adding the piece square multiplied by 5 * 2 (5 piece types, 2 options for piece owner)
- adding the piece type ofset by 5 if it is an enemy piece
- if we are looking from the enemy point of view, ofset by 40960

Lets do an example for the above position
First the white pawn:
Our king is on square 8, our pawn on square 17, our piece type is 6 (queen is 2, rook is 3, bishop is 4, knight 5, pawn 6), and no ofset since it is our piece.
The index would be equal to 8*64*5*2 + 17*5*2 + 6 = 5296 which is the 3rd number above. (rooks are computed before pawns)
