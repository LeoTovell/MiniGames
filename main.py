import pygame
import random
import time
import os
from queue import PriorityQueue
import ctypes
from pygame.locals import *
from os import listdir
from os.path import isfile, join
ctypes.windll.kernel32.SetConsoleTitleW("MonsterGame")

pygame.init()
pygame.font.init()

WIDTH = 800
#Square window size
x = 1300
y = 300

os.environ['SDL_VIDEO_WINDOW_POS'] = "%d,%d" % (x,y)

window = pygame.display.set_mode([WIDTH, WIDTH+100]) #Setting window to Width x Width

#colours
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 255, 0)
YELLOW = (255, 255, 0)
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
PURPLE = (128, 0, 128)
ORANGE = (255, 165 ,0)
GREY = (128, 128, 128)
TURQUOISE = (64, 224, 208)
GOLD = (255,215,0)
BROWN = (150,75,0)

class Level:
    def __init__(self, name, monsterPos, playerPos, goldPos, trapsPos, wallsPos):
        self.name = name
        self.monsterPos = monsterPos
        self.playerPos = playerPos
        self.goldPos = goldPos
        self.trapsPos = trapsPos
        self.wallsPos = wallsPos

class Node: #Create each 'square'
    def __init__(self, row, col, WIDTH, total_rows):
        self.row = row
        self.col = col
        self.y = row * WIDTH
        self.x = col * WIDTH
        self.colour = WHITE
        self.width = WIDTH
        self.total_rows = total_rows

    def get_pos(self):
        return self.row, self.col

    def make_empty(self):
        self.colour = WHITE

    def make_player(self):
        self.colour = RED
    
    def make_wall(self):
        self.colour = GREY
    
    def make_monster(self):
        self.colour = PURPLE
        
    def make_trap(self):
        self.colour = BROWN
    
    def make_gold(self):
        self.colour = GOLD

    def make_exit(self):
        self.colour = BLACK

    def make_vis_monster(self):
        self.colour = PURPLE

    def is_wall(self):
        return self.colour == GREY

    def is_player(self):
        return self.colour == RED

    def is_monster(self):
        return self.colour == PURPLE

    def is_gold(self):
        return self.colour == GOLD

    def is_trap(self):
        return self.colour == BROWN

    def is_exit(self):
        return self.colour == BLACK

    def update_neigbours(self, grid):
        self.neighbours = []
        if self.row < self.total_rows -1 and not grid[self.row + 1][self.col].is_wall():
            self.neighbours.append(grid[self.row + 1][self.col])

        if self.row > 0 and not grid[self.row - 1][self.col].is_wall():
            self.neighbours.append(grid[self.row - 1][self.col])

        if self.col < self.total_rows -1 and not grid[self.row][self.col + 1].is_wall():
            self.neighbours.append(grid[self.row][self.col + 1])

        if self.col > 0 and not grid[self.row][self.col - 1].is_wall():
            self.neighbours.append(grid[self.row][self.col - 1])

    def draw(self, window):
        pygame.draw.rect(window, self.colour, (self.x, self.y, self.width, self.width))
        
#debug = int(input("Debug mode (1/0): "))
debug = 0
#loadFromData = int(input("Would you like to load a level (1/0): "))
loadFromData = 0
if loadFromData == 0:
    loadFromData = True
    default = True
elif loadFromData == 1:
    loadFromData = True
    default = False

playerPos = [5,0]
wallsPos = [[0,1],[1,1],[2,1],[3,1],[1,5],[2,6],[5,3],[5,4],[5,5],[5,6]]
monsterPos = [3,5]
goldPos = [[2,5],[2,7],[2,4],[2,3]]
trapsPos = [[2,2],[2,6],[5,2]]
score = 0
goldRemaining = True
awake = False
replaceGold = False

def levelComplete():
    global window
    print("You completed the level! Continue on now.")
    myfont = pygame.font.SysFont('Comic Sans MS', 30)
    textsurface = myfont.render('Level complete', False, (0,0,0))
    window.fill((255,0,0))
    window.blit(textsurface, (400,400))
    pygame.display.update()
    time.sleep(0.5)
    window.fill((0,255,0))
    window.blit(textsurface, (600,400))
    pygame.display.update()
    time.sleep(0.5)
    window.fill((0,0,255))
    window.blit(textsurface, (300,400))
    pygame.display.update()
    time.sleep(0.5)
    
    

    

def make_grid(rows, WIDTH):
    grid = []
    gap = WIDTH // rows
    for i in range(rows):
        grid.append([])
        for j in range(rows):
            node = Node(i, j, gap, rows)
            grid[i].append(node)
    return grid

def draw_grid(window, rows, WIDTH):
    gap = WIDTH // rows
    for i in range(rows + 1):
        pygame.draw.line(window, GREY, (0, i * gap), (WIDTH, i * gap))
        for j in range(rows):
            pygame.draw.line(window, GREY, (j * gap, 0), (j * gap, WIDTH))

def placeTiles(grid):
    for i in wallsPos:
        grid[i[0]][i[1]].make_wall()
    for i in goldPos:
        grid[i[0]][i[1]].make_gold()
    for i in trapsPos:
        grid[i[0]][i[1]].make_trap()
        
    grid[playerPos[0]][playerPos[1]].make_player()
    grid[monsterPos[0]][monsterPos[1]].make_monster()

def getPlayerMove():

    if playerPos[0] < 7 and not grid[playerPos[0] + 1][playerPos[1]].is_wall():
        availableMove.append("d")
    if playerPos[0] > 0 and not grid[playerPos[0] - 1][playerPos[1]].is_wall():
        availableMove.append("u")
    if playerPos[1] < 7 and not grid[playerPos[0]][playerPos[1] + 1].is_wall():
        availableMove.append("r")
    if playerPos[1] > 0 and not grid[playerPos[0]][playerPos[1] -1].is_wall():
        availableMove.append("l")

##    print("Available moves: ", end="")
##    for i in range(len(availableMove)):
##        print(availableMove[i], end="  ")
##    print("")

    y = 0
    x = 0

    move = False

    while not move:
        for event in pygame.event.get():
            if event.type == KEYDOWN:
                if (event.key == 1073741903 or event.unicode == 'd') and ("r" in availableMove):
                    x = 1
                    move = True
                if (event.key == 1073741904 or event.unicode == 'a') and ("l" in availableMove):
                    x = -1
                    move = True
                if (event.key == 1073741905 or event.unicode == 's') and ("d" in availableMove):
                    y = 1
                    move = True
                if (event.key == 1073741906 or event.unicode == 'w') and ("u" in availableMove):
                    y = -1
                    move = True
                
    grid[playerPos[0]][playerPos[1]].make_empty()
    playerPos[0] = playerPos[0] + y
    playerPos[1] = playerPos[1] + x

    if grid[playerPos[0]][playerPos[1]].is_exit():
        levelComplete()
    grid[playerPos[0]][playerPos[1]].make_player()

def checkMonster():
    if playerPos[0] == monsterPos[0] and playerPos[1] == monsterPos[1]:
        grid[playerPos[0]][playerPos[1]].make_vis_monster()
        pygame.display.update()
        print("You ran into the monster!")
        time.sleep(2)
        quit()

def checkGold(score):
    goldRemaining = True
    for i in range(0,len(goldPos)):
        if playerPos[0] == goldPos[i][0] and playerPos[1] == goldPos[i][1]:
            goldPos[i][0] = ""
            goldPos[i][1] = ""
            print("You hit some gold!")
            grid[playerPos[0]][playerPos[1]].make_player()
            score = score + 1
    if score == len(goldPos):
        print("You collcted all of the gold!")
        goldRemaining = False
        grid[0][0].make_exit()
    return score, goldRemaining

def checkTraps():
    awake = False
    for i in range(0,3):
        if playerPos[0] == trapsPos[i][0] and playerPos[1] == trapsPos[i][1]:
            print("You hit a trap, the monster is now awake!")
            for i in range(0,3):
                grid[trapsPos[i][0]][trapsPos[i][1]].make_empty()
                trapsPos[i][0] = ""
                trapsPos[i][1] = ""
            grid[monsterPos[0]][monsterPos[1]].make_vis_monster()
            print(grid[monsterPos[0]][monsterPos[1]].get_pos())
            grid[playerPos[0]][playerPos[1]].make_player()
            awake = True
    return awake

def h(p1,p2):
    x1,y1 = p1
    x2,y2 = p2
    return abs(x1 - x2) + abs(y1-y2)

def reconstruct_path(came_from, current):
    monsterPath = []
    while current in came_from:
        current = came_from[current]
        monsterPath.append(current)
    return monsterPath

def monsterMove(grid, start, end, replaceGold):
    global playerPos
    for row in grid:
        for node in row:
            node.update_neigbours(grid)
            
    count = 0
    open_set = PriorityQueue()
    open_set.put((0, count, start))
    came_from = {}
    g_score = {node: float("inf") for row in grid for node in row}
    g_score[start] = 0
    f_score = {node: float("inf") for row in grid for node in row}
    f_score[start] = h((monsterPos[0], monsterPos[1]), (playerPos[0], playerPos[1]))

    open_set_hash = {start}

    
    while not open_set.empty():

        current = open_set.get()[2]
        open_set_hash.remove(current)

        if current == end:
            monsterPath = reconstruct_path(came_from, playerNode)

        for neighbour in current.neighbours:
            temp_g_score = g_score[current] + 1

            if temp_g_score < g_score[neighbour]:
                came_from[neighbour] = current
                g_score[neighbour] = temp_g_score
                f_score[neighbour] = temp_g_score + h(neighbour.get_pos(), playerNode.get_pos())
                if neighbour not in open_set_hash:
                    count += 1
                    open_set.put((f_score[neighbour], count, neighbour))
                    open_set_hash.add(neighbour)

    try:
        #moving the `
        monsterMove = monsterPath[-2].get_pos()
        #wrk out direction of monsterPath[-1] from current monsterPos. Move accordingy

        x = 0
        y = 0
        
        if monsterPos[0] < monsterMove[0]:
            y = 1
        elif monsterPos[0] > monsterMove[0]:
            y = -1
        elif monsterPos[1] < monsterMove[1]:
            x = 1
        elif monsterPos[1] > monsterMove[1]:
            x = -1

        grid[monsterPos[0]][monsterPos[1]].make_empty() #Removes monster from previous place

        monsterPos[0] = monsterPos[0] + y #updates x
        monsterPos[1] = monsterPos[1] + x #updates y
        
        if grid[monsterPos[0]][monsterPos[1]].is_gold(): #if new pos is gold
            replaceGold = True #set so replaced next round

        grid[monsterPos[0]][monsterPos[1]].make_vis_monster()

        if replaceGold:
            print("replacing gold")
            grid[monsterPos[0] - y][monsterPos[1] - x].make_gold()
            replaceGold = False

    
    except:
        grid[playerPos[0]][playerPos[1]].make_vis_monster()
        pygame.display.update()
        print("The monster has caught you!")
        pygame.time.wait(5)
        quit()

    return replaceGold

def clearVariables():
    global playerPos, wallsPos, monsterPos, goldPos, trapsPos
    playerPos = []
    wallsPos = []
    monsterPos = []
    goldPos = []
    trapsPos = []

def updatePlayerNodes():
    monsterNode = grid[monsterPos[0]][monsterPos[1]]
    playerNode = grid[playerPos[0]][playerPos[1]]
    return monsterNode, playerNode

def getCoordinate(line):
    y = int((line.strip()[2:].partition(","))[0])
    x = int((line.strip()[2:].partition(","))[2])
    array = [y,x]
    return array

def newLevel(Level):
    files = [f for f in listdir("levels") if isfile(join("levels", f))]
    count = 1
    for i in files:
        print(f"{count}: {i}")
        count += 1
    choice = int(input(f"Please enter level number (1-{count}: "))


loading = pygame.image.load('loading.jpg')
window.blit(loading, (((WIDTH/2)-320,(WIDTH/2)-180)))
pygame.display.update()
newLevel(Level)

if loadFromData:
    clearVariables()
    count = 0
    if not default:
        filename = input("Enter exact filename: ")
    if default:
        filename = "levels/defaultlevel.txt"
    with open(filename, "r") as file:
        for line in file:
            if count==0:
                levelName = line.strip()
            if line.strip()[0] == "g":
                goldPos.append(getCoordinate(line))
            if line.strip()[0] == "t":
                trapsPos.append(getCoordinate(line))
            if line.strip()[0] == "w":
                wallsPos.append(getCoordinate(line))
            if line.strip()[0] == "p":
                coords = getCoordinate(line)
                playerPos.insert(0,coords[0])
                playerPos.insert(1,coords[1])
            if line.strip()[0] == "m":
               coords = getCoordinate(line)
               monsterPos.insert(0,coords[0])
               monsterPos.insert(1,coords[1])
            if 'str' in line:
                break
            count += 1

def draw_score(window, width):
    global score
    global awake
    global goldPos
    levelNumber = 1
    for i in range(0,len(goldPos)):
        pass
    font = pygame.font.SysFont('Comic Sans MS', 30)
    gold = font.render(f'Gold Collected: {score}/{i+1}', False, (0,0,0))
    level = font.render(f'Level: {levelNumber}', False, (0,0,0))
    pygame.draw.rect(window, (0,0,0), (560,width+28,180,40))
    leveleditor = font.render(f'Level Editor', False, (255,255,255))
    window.blit(leveleditor,(565,width+25))
    window.blit(level,(350,width+25))
    window.blit(gold, (20,width+25))
    pygame.display.update()

def draw(window, grid, rows, WIDTH):
    window.fill(WHITE)

    for row in grid:
        for node in row:
            node.draw(window)

    draw_grid(window, rows, WIDTH)
    draw_score(window, WIDTH)
    pygame.display.update()


rows = 8
grid = make_grid(rows, WIDTH)
placeTiles(grid)

if debug == 0:
    debug = False

elif debug == 1:
    debug = True
    grid[monsterPos[0]][monsterPos[1]].colour = PURPLE
    for i in trapsPos:
        grid[i[0]][i[1]].display = BROWN
    for i in goldPos:
        grid[i[0]][i[1]].display = GOLD

running = True
while running:
    draw(window, grid, rows, WIDTH)

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    availableMove = []
    getPlayerMove()
    checkMonster()
    if goldRemaining:
        score, goldRemaining = checkGold(score)
    monsterNode, playerNode = updatePlayerNodes()
    if not awake:
        awake = checkTraps()
    elif awake:
        replaceGold = monsterMove(grid, monsterNode, playerNode, replaceGold)


pygame.quit()

