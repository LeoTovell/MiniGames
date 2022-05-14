import pygame
import math
import random
import time
pygame.init()

blockPerRow = int(input("Enter blocks per row: "))
rows = int(input("Enter rows of blocks: "))


window = pygame.display.set_mode([1920,1080])
clock = pygame.time.Clock()
black = (0,0,0)
x = 800
paddleWidth = 150
step = 10
centreX = 800
centreY = 600
ballDirection = 164#random.randint(1,360)
ballSpeed = 5 #constant
score = 0


class Block:
    def __init__(self, x, y, width, height, col, row):
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.disabled = False

    def destroy(self):
        global score
        score += 1
        self.disabled = True

    def draw(self):
        if not self.disabled:
            pygame.draw.rect(window, (0,0,255), (self.x, self.y, self.width, self.height))

    def checkHit(self,centreY, centreX):
        global ballDirection
        if centreY+10 > self.x and centreY-20 < self.x+self.width and centreX+10 > self.y and centreX-10 < self.y + self.height and not self.disabled:
            if ballDirection < 270 and ballDirection > 180:
                ballDirection = abs(180 + (360-ballDirection))
            elif ballDirection < 90 and ballDirection > 0:
                ballDirection = abs(180 - (ballDirection - 180))
            elif ballDirection < 180 and ballDirection > 90:
                ballDirection = abs(180-ballDirection)
            elif ballDirection < 360 and ballDirection > 270:
                ballDirection = abs(180 - ballDirection)
            return True


blockX = 100
blockY = 100
gap = 20
blockWidth = (1720//(blockPerRow) - gap)
blockList = []
for i in range(rows):
    for j in range(blockPerRow):
        block = Block(blockX, blockY, blockWidth, 50, i, j)
        blockList.append(block)
        blockX += blockWidth + gap
    blockX = 100
    blockY = blockY + 80


for block in blockList:
    block.draw()

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                running = False
    window.fill(black)
    for block in blockList:
        block.draw()

    font = pygame.font.SysFont('Comic Sans MS', 30)
    ballDirectionText = font.render(f'{ballDirection}', False, (255,255,255))
    window.blit(ballDirectionText,(1920/2-10,1080/2-100))


    centreX += round(math.cos(math.radians(ballDirection)) * ballSpeed,3)
    centreY += round(math.sin(math.radians(ballDirection)) * ballSpeed,3)

    pygame.draw.circle(window, (0,255,0), (centreY, centreX), 10)

    mouseX = pygame.mouse.get_pos()[0]
    if mouseX >= 1920-paddleWidth:
        mouseX = 1920-paddleWidth
    if mouseX <= 0:
        mouseX = 0

    collided = False
    if centreX+10 >= 1070:
        running = False
    if centreX-10 <= 0:
        if ballDirection > 180:
            ballDirection = 180 + (360-ballDirection)
        if ballDirection <= 180:
            ballDirection = 180-ballDirection
    if centreY-10 <= 0:
        ballDirection = 360-ballDirection
    if centreY+10 >= 1920:
        ballDirection = 180 - (ballDirection - 180)
    if centreX+10 >= 1000 and (centreY+10 > mouseX and centreY-10 < mouseX+paddleWidth):
        angleAppend = 0
        if centreY+10 > mouseX and centreY+10 < mouseX+1*(paddleWidth/3):
            angleAppend = random.randint(5,15)
        if centreY+10 > mouseX+1*(paddleWidth/3) and centreY+10 < mouseX+2*(paddleWidth/3):
            angleAppend = 0
        if centreY+10 > mouseX+2*(paddleWidth/3) and centreY+10 < mouseX+3*(paddleWidth/3):
            angleAppend = -(random.randint(5,15))
        if ballDirection > 180:
            ballDirection = 180 + (360-ballDirection) + angleAppend
        if ballDirection < 180:
            ballDirection = 180-ballDirection + angleAppend

    for block in blockList:
        if block.checkHit(centreY, centreX):
            block.destroy()

    scoreText = font.render(f'Blocks Destroyed: {score}', False, (255,255,255))
    window.blit(scoreText, (1920/2-10,1080/2))

    pygame.draw.rect(window, (255,255,255), (mouseX, 1000, paddleWidth,10))
    pygame.draw.rect(window, (255,0,0), (0,1070, 1920,10)) #DEATH LINE

    pygame.display.update()
    clock.tick(120)


pygame.quit()
print("Game over!")
