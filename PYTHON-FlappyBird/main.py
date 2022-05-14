#Flappy Bird
#6 obstacles per screen,


import pygame
import math
import random

pygame.init()
clock = pygame.time.Clock()


window = pygame.display.set_mode((1920,1080))

class obstacle:
    def __init__(self, topBlock, bottomBlock):
        self.top = topBlock
        self.bottom = bottomBlock
        self.x = 1920

    def move(self):
        self.x -=5

gap = 300
obstacleList = []
createNextObject = False
centreY = 1080/2

topBlock = (random.randint(1,500))
bottomBlock = (topBlock+gap)
b = obstacle(topBlock, bottomBlock)
obstacleList.append(b)

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    jump = False
    window.fill((0,0,0))

    #render environment
    if obstacleList[-1].x < 1400:
        topBlock = (random.randint(1,500))
        bottomBlock = (topBlock+gap)
        b = obstacle(topBlock, bottomBlock)
        obstacleList.append(b)

    if obstacleList[0].x <-200:
        obstacleList.pop(0)


    for object in obstacleList:
        pygame.draw.rect(window, (255,255,255), (object.x, 0, 200, object.top))
        pygame.draw.rect(window, (255,255,255), (object.x, object.bottom, 200, 1080-gap))
        object.move()

    #take input
    key_input = pygame.key.get_pressed()
    if key_input[pygame.K_SPACE]:
        jump = True

    #render bird

    if jump:
        centreY -= 10
    else:
        centreY+=8

    #check collisions

    bird = pygame.image.load("bird.png")
    bird = pygame.transform.scale(bird, (200,80))
    window.blit(bird, (1920/2-350, centreY))

    for object in obstacleList:
        print(object.bottom)
        print(object.top)
        print("\n")
        if centreY > object.bottom and centreY < object.top:
            print("cool")


    clock.tick(60)
    pygame.display.update()
