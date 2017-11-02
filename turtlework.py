import turtle
s = turtle.Screen()
t = turtle.Turtle()
tp = turtle.pos()


turtle1 = turtle.Turtle(shape='turtle')
turtle1.color('red')
turtle1.speed(2)  # = 3
turtle1.penup()

turtle2 = turtle.Turtle(shape='turtle')
turtle2.color('blue')
turtle2.speed(2)  # "slow" (3) < 4 < "normal" (6)
turtle2.penup()

t.speed(0)
t.penup()
t.setpos(0,0)
t.pendown()
t.circle(58)



#Total runtime for T1=75s
#Total runtime for T1=65s
totalTimeOne = 75
timeOne = int(input("What is the time you want for T1 with a total time of %s?:"%totalTimeOne))
angleOne = int((((timeOne/totalTimeOne)*58)/58)*360)

totalTimeTwo = 65
timeTwo = int(input("What is the time you want for T2 with a total time of %s?:"%totalTimeTwo))
angleTwo = int((((timeTwo/totalTimeTwo)*58)/58)*360)
def redTurtle(t, angleOne):
    for x in range(angleOne):
        turtle1.pensize(10)
        turtle1.pencolor('red')
        turtle1.speed(0)
        turtle1.forward(1)
        turtle1.left(1)
redTurtle(t,angleOne)

def blueTurtle(t, angleTwo):
    for x in range(angleTwo):
        turtle2.pensize(10)
        turtle2.pencolor('blue')
        turtle2.speed(0)
        turtle2.forward(1)
        turtle2.left(1)
blueTurtle(t, angleTwo)
