#Monster Game
#Init game variables
import random
import time
#var = ["displayLetter", tileType]
#0 = empty #1 = player #2 = gold #3 = monster #4 = trap #5 = door
#todo:
# - Fix bug where two traps can generate as the same coordinates (compare to other traps too).
# - Win conditions.
# - Make monster chase you rather than move in unity with you.


debug = int(input("Debug mode (1/0): "))
if debug == 0:
    debug = False
    empty = ["-", 0]
    player = ["P", 1]
    monster = ["-", 3]
    gold = ["-", 2]
    trap = ["-", 4]
elif debug == 1:
    debug = True
    empty = ["-", 0]
    player = ["P", 1]
    monster = ["M", 3] #change to "-" for a debug real game.
    gold = ["G", 2]
    trap = ["T", 4]

grid = [[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty]]

playerPos = [0,0]
monsterPos = ["",""]
availableMove = []
goldPos = [[7,7],[7,7],[7,7]]
trapsPos = [[7,7],[7,7],[7,7]]
score = 0
rowCount = 0
placeDoor = False
levelScore = 0
scoreAnnouncment = True
monsterAwake = False

print(grid)

def generateMonster():
    monsterPos[0] = random.randint(2,5)
    monsterPos[1] = random.randint(2,5)
    monster[0] = "-"
    return monsterPos, monster

def generateGold():
    i = 0
    while i < 3: #change 3 to number of gold
        goldPos[i][0] = random.randint(1,7)
        goldPos[i][1] = random.randint(1,7)
        if debug:
            print(f"gold({i}) = [{goldPos[i][0]}][{goldPos[i][1]}]")
        if ((goldPos[i][0] == monsterPos[0]) and (goldPos[i][1] == monsterPos[1])):
            i = i - 1
        else:
            i = i + 1

def generateTraps():
    i = 0
    while i < 3: #change 3 to number of traps
        trapsPos[i][0] = random.randint(1,7)
        trapsPos[i][1] = random.randint(1,7)
        if debug:
            print(f"trap({i}) = [{trapsPos[i][0]}][{trapsPos[i][1]}]")
        if (((trapsPos[i][0] == goldPos[0][0]) and (trapsPos[i][1] == goldPos[0][1])) or ((trapsPos[i][0] == goldPos[1][0]) and (trapsPos[i][1] == goldPos[1][1])) or ((trapsPos[i][0] == goldPos[2][0]) and (trapsPos[i][1] == goldPos[2][1])) or ((trapsPos[i][0] == monsterPos[0]) and (trapsPos[i][1] == monsterPos[1]))):
            if debug:
                print("Matched, redone.")
        else:
            i = i + 1
        

def placeTiles():
    grid[goldPos[0][0]][goldPos[0][1]] = gold
    grid[goldPos[1][0]][goldPos[1][1]] = gold
    grid[goldPos[2][0]][goldPos[2][1]] = gold

    grid[trapsPos[0][0]][trapsPos[0][1]] = trap
    grid[trapsPos[1][0]][trapsPos[1][1]] = trap
    grid[trapsPos[2][0]][trapsPos[2][1]] = trap

    grid[playerPos[0]][playerPos[1]] = player
    grid[monsterPos[0]][monsterPos[1]] = monster

move = False

def display():
    for i in range(8):
        print()
        for j in range(8):
            print("  ", grid[i][j][0], end="")
    print("\n\n\n")

def resetGame():
    global playerPos, grid
    grid = [[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty],[empty,empty,empty,empty,empty,empty,empty,empty]]
    playerPos[0] = 0
    playerPos[1] = 0
    generateMonster()
    generateGold()
    generateTraps()
    placeTiles()
    if debug:
        print(f"Monster = [{monsterPos[0]}][{monsterPos[1]}]")
    return grid

def pathfind():
    global monsterPos, runOnlyOnce, playerPos, grid
    runOnlyOnce = True
    grid[monsterPos[0]][monsterPos[1]] = empty
    if ((monsterPos[1] == playerPos[1]) and runOnlyOnce):
        if ((monsterPos[0] > playerPos[0]) and runOnlyOnce):
            monsterPos[0] = monsterPos[0] - 1
            runOnlyOnce = False
        elif ((monsterPos[0] < playerPos[0]) and runOnlyOnce):
            monsterPos[0] = monsterPos[0] + 1
            runOnlyOnce = False
    elif ((monsterPos[0] == playerPos[0]) and runOnlyOnce):
        if ((monsterPos[1] > playerPos[1]) and runOnlyOnce):
            monsterPos[1] = monsterPos[1] - 1
            runOnlyOnce = False
        elif ((monsterPos[1] < playerPos[1]) and runOnlyOnce):
            monsterPos[1] = monsterPos[1] + 1
            runOnlyOnce = False
    if ((monsterPos[1] > playerPos[1]) and runOnlyOnce):
        monsterPos[1] = monsterPos[1] - 1
        runOnlyOnce = False
    elif ((monsterPos[1] < playerPos[1]) and runOnlyOnce):
        monsterPos[1] = monsterPos[1] + 1
        runOnlyOnce = False
    elif ((monsterPos[0] > playerPos[0]) and runOnlyOnce):
        monsterPos[0] = monsterPos[0] - 1
        runOnlyOnce = False
    elif ((monsterPos[0] < playerPos[0]) and runOnlyOnce):
        monsterPos[0] = monsterPos[0] + 1
        runOnlyOnce = False

    if ((playerPos[0] == monsterPos[0]) and (playerPos[1] == monsterPos[1])):
        print("The monster caught you...")
        resetGame()
    grid[monsterPos[0]][monsterPos[1]] = monster

def movePlayer(y, x):
    global playerPos, monsterPos, score, grid, levelScore, scoreAnnouncment, monsterAwake
    grid[playerPos[0]][playerPos[1]] = empty
    playerPos[0] = playerPos[0] + y
    playerPos[1] = playerPos[1] + x

    if ((playerPos[0] == monsterPos[0]) and (playerPos[1] == monsterPos[1])):
        print("You ran into the monster.")
        time.sleep(5)
        resetGame()

    elif ((playerPos[0] == goldPos[0][0]) and (playerPos[1] == goldPos[0][1]) or (playerPos[0] == goldPos[1][0]) and (playerPos[1] == goldPos[1][1]) or (playerPos[0] == goldPos[2][0]) and (playerPos[1] == goldPos[2][1])):
        score += 1
        print(f"You ran into some gold, +1 score! {3 - score} nuggets o' gold remaining!")
        for i in range(0,3):
            if ((playerPos[0] == goldPos[i][0]) and (playerPos[1] == goldPos[i][1])):
                goldPos[i][0] = ""
                goldPos[i][1] = ""
    elif ((playerPos[0] == trapsPos[0][0]) and (playerPos[1] == trapsPos[0][1]) or (playerPos[0] == trapsPos[1][0]) and (playerPos[1] == trapsPos[1][1]) or (playerPos[0] == trapsPos[2][0]) and (playerPos[1] == trapsPos[2][1])):
        print("You hit a trap, the monster is awake and hunting you down!")
        for i in range(0,3):
            grid[trapsPos[i][0]][trapsPos[i][1]] = empty
        monsterAwake = True
        print("Monster Awake")
        monster[0] = "M"
    grid[playerPos[0]][playerPos[1]] = player

    if score == 3:
        print("All gold collected.")
        resetGame()
        levelScore += 1

    if monsterAwake:
        pathfind()
        #pass
#    if ((score == 3) and (scoreAnnouncment)):
#        print("You collected all the gold, you win!")
#        scoreAnnouncment = False
#        for row in grid:
#            if row == 3:
#                print("hey")
#                grid.insert(8, "d")
    
    return playerPos

if __name__ == "__main__":
    generateMonster()
    generateGold()
    generateTraps()
    placeTiles()
    if True:
        print(f"Monster = [{monsterPos[0]}][{monsterPos[1]}]")
    pass
          
while True:
    display()
    if score == 3:
        print("You collected all the gold, you win!")
        for row in grid:
            if row == 3:
                grid.insert(8, "d")
    availableMove = []
    if playerPos[0] < 7:
        availableMove.append("d")
    if playerPos[0] > 0:
        availableMove.append("u")
    if playerPos[1] < 7:
        availableMove.append("r")
    if playerPos[1] > 0:
        availableMove.append("l")

    print("Available moves: ", end="")
    for i in range(len(availableMove)):
        print(availableMove[i], end="  ")
    print("")

    move = (input("Enter direction to move 1 unit in: ")).lower()
    while move not in availableMove:
        move = (input("Invalid Choice, Enter direction to move 1 unit in: ")).lower()

    if move == "l":
        movePlayer(0, -1)
    if move == "r":
        movePlayer(0, 1)
    if move == "u":
        movePlayer(-1, 0)
    if move == "d":
        movePlayer(1, 0)
    
    print("\n\n\n\n\n\n\n")