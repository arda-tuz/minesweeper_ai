# Minesweeper Solver Agent Algorithm Details

The algorithm consists of three main components and operates in a loop:

## Arrow Algorithm

The Arrow algorithm has two sub-components:

### 1. ArrowFlag Algorithm
- Operates with O(N²) complexity
- Scans the entire game matrix to identify definite mine locations
- Counts open and flagged squares around a square
- If the number of mines around a square equals the number of flags and there are still unopened squares, these squares are definitely safe
- The agent's flag placement operations only occur through this algorithm

### 2. ArrowOpen Algorithm  
- Operates with O(N²) complexity
- Works similar to ArrowFlag logic
- Counts open and flagged squares around a square
- If the number of mines around a square equals the number of flags, all remaining squares are safe and can be opened
- When any square is opened, the Arrow algorithm returns to start and runs again

## Brain Algorithm

If the Arrow algorithm cannot open any squares, the Brain algorithm activates:

1. **Information Gathering Phase:**
  - O(N²) complexity matrix scan is performed
  - Squares are divided into two categories:
    - Squares with known information (at least one neighbor is open)
    - Squares with unknown information

2. **Subset Division:**
  - Squares with known information are divided into independent subsets
  - Two squares are in different subsets if:
    - Opening one doesn't affect information about the other
    - There is no information dependency between them

3. **Backtracking Enumeration:**
  - Backtracking is performed independently for each subset
  - For N total squares, 2^N possible states are checked
  - The following constraints are checked in each state:
    - Total mine count limit
    - Flag map compatibility
    - Open squares map compatibility

4. **Decision Making:**
  - All possible combinations are evaluated
  - If a square is empty in all combinations, that square is opened
  - Returns to Arrow algorithm

## Dice Algorithm

If the Brain algorithm also cannot open squares, the Dice algorithm activates:

1. **Probability Calculation:**
  - Uses backtracking results from the Brain algorithm
  - For each square:
    - Number of combinations containing mines
    - Number of combinations without mines is recorded
  
2. **Sample Calculation:**
  - Total 200 combinations
  - Square A: mine in 50 combinations, empty in 150
  - Square B: mine in 20 combinations, empty in 180
  - Square B is safer choice (10% mine probability vs 25%)

3. **Guess and Result:**
  - Square with lowest mine probability is selected
  - Selected square is opened
  - If a mine is hit, game is lost (only way for agent to lose)
  - If no mine, returns to Arrow algorithm

The combination of these three algorithms enables the agent to achieve high success rates especially at beginner and intermediate levels. The drop in performance at Expert level is mainly due to the increased need to use the Dice algorithm and the relative simplicity of the probability calculation method compared to more sophisticated methods like Markov chains.
