#!/usr/bin/python3
import os
import subprocess

def parcours(name : str):
    cmp = 0
    liste = os.listdir((name))
    for el in liste:
        new_name = name+'/'+el
        if (os.path.isdir(new_name) and el[0] !='.'):
            parcours(new_name)
        elif (os.path.isfile(new_name)):
            print(new_name+":")
            result = subprocess.run("java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main "+name + '/' + el, shell=True)
            print()

print("GOOD : ")
parcours(os.getcwd()+"/"+"Tests"+"/Good")

print("BAD : ")
parcours(os.getcwd()+"/"+"Tests"+"/Bad")
