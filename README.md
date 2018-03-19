**Manual for Spider-Solitaire** 

This is version of game with one suit

**Cards**

Cards have four distinct suits: hearts, clubs, diamonds, and spades. As well, there are 13 unique ranks: the king, the queen, the jack, the ten, the 9, the 8, the 7, the 6, the 5, the 4, the 3, the 2, and finally the ace. Each card has a suit and a rank and a deck consists of a complete set of each of the 52 unique cards.

**Layout**

The table can be broken up into three basic areas, the tableau, the stock, and the foundation. In this version you are only able to see the tableau. For foundations and stock you are able to see the count of cards.

**The tableau**

The tableau is the in-play area where most of the action takes place. It's sectioned horizontally into 10 equal-sized unmarked areas called columns, sometimes referred to slots. Each column is simply an area where a pile of cards is usually situated.

**The stock**

The stock is where the undealt cards are kept. In order to deal cards to begin a new round the player clicks in this area, which causes 10 cards to be dealt face-up into the tableau, one into each column. However, for a deal to succeed there must be at least one card in every column.

**The foundation**

The foundation is where assembled suits are displayed.

**Starting layout**

The computer will deal a total of 44 face-down cards, 5 into each of the first 4 columns and 4 into each of the remaining 6 columns.  These are most commonly referred to as either hidden cards or non-exposed cards because only the back of each card can be seen, which hides its rank and suit.Immediately after all the hidden cards are dealt, one face-up card is dealt into each of the 10 columns. These are usually called visible cards or exposed cards because their ranks and suits are exposed for the player to see.

**Run**

Every rank has an assigned numerical value. The king is worth the most, then the queen is worth 1 less, then the jack, the ten, and so on down to the ace, which is worth the least.Cards can be, and often are, ordered by their ranks.

This is accomplished by arranging them in order so that the value of each card's rank is one off from both its predecessor and its successor. There are two difference ways to do this, either ascending or descending; however, in Spider Solitaire, only the latter way is significant. 

Consecutive cards within a column which are ordered by rank so that the value of each rank is exactly 1 less than the rank before are collectively called a run. For example, a run might consist of a king followed by a queen and then a jack, which would be a 3-card run. The king would be the highest in its column as seen on the screen and the jack, the lowest. 


**How to play**

The key objective of the game is to order a full set of all the 13 unique ranks, from the king down to the ace. So with 104 total cards, 8 builds are possible. When all 8 builds are assembled, no cards remain in the tableau and the game is won. In effect, one could claim that the object of the game is to remove all of the cards from the tableau.

To play the you have following commands to use:

1. move : use to move cards, this option has 3 parameters (source column, index of card from source column, destination column)
2. take : use to take cards from stock
3. revert : use to revert one step of game, can be used till origin state of game
4. exit : use to end the game
5. restart : use to restart game

**Score**

The score is calculated per every full run. If you complete run below than 20 steps (take or move action) you are rewarded by 100 points. If you complete run with more than 20 steps your points are calculated by following formula : 100 - NUMBER_OF_STEPS.

So your final score is POINTS_PER_ROW * 8.