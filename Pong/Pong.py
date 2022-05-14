#Pygame pong

import math
import pygame
import random
import time
pygame.init()

WIDTH = 1200
HEIGHT = 800

screen = pygame.display.set_mode([WIDTH, HEIGHT])
clock = pygame.time.Clock()
y1 = 350
y = 350
ballSpeed = 8 #constant
ballPos = [400,400]#y,x
ballDirection = 47 #degrees, 0 is up
ballStep = 100
regStep = 7.5
centreX = 400
centreY = 600
    

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    key_input = pygame.key.get_pressed()
    if key_input[pygame.K_DOWN] and not (y1 >= 700):
        y1 += regStep
    if key_input[pygame.K_UP] and not (y1 <= 0):
        y1 -= regStep
    if key_input[pygame.K_w] and not (y <= 0):
        y -= regStep
    if key_input[pygame.K_s] and not (y >= 700):
        y += regStep
        
    screen.fill((255,255,255))

    pygame.draw.rect(screen,(0,0,0),(80,y,20,100)) #left paddle
    pygame.draw.rect(screen,(0,255,0),(1100,y1,20,100)) #right paddle

    #Collision detector
    collided = False
    if centreX+20 >= 800:
        if ballDirection > 180:
            ballDirection = 180 + (360-ballDirection)
        if ballDirection < 180:
            ballDirection = 180-ballDirection
    if centreX-20 <= 0:
        if ballDirection > 180:
            ballDirection = 180 + (360-ballDirection)
        if ballDirection < 180:
            ballDirection = 180-ballDirection
    if centreY-20 <= 0:
        centreY = 600
        centreX = 400
        ballDirection = random.choice([45,315])
    if centreY+20 >= 1200:
        centreY = 600
        centreX = 400
        ballDirection = random.choice([45,315])
    if centreY+20 >=1100 and centreY-20 <= 1120 and centreX-20 >= y1 and centreX + 20 <= y1+100:
        ballDirection = 360-ballDirection
    if centreY-20 <= 100 and centreY+20 >= 80 and centreX-20 >= y and centreX + 20 <= y+100:
        ballDirection = 180 - (ballDirection - 180)

    centreX += round(math.cos(math.radians(ballDirection)) * ballSpeed,3)
    centreY += round(math.sin(math.radians(ballDirection)) * ballSpeed,3)

    pygame.draw.circle(screen, (255,0,0),(centreY,centreX), 20)
  
    pygame.display.flip()
    clock.tick(60)
    
pygame.quit()
    
