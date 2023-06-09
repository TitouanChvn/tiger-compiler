#!/usr/bin/python3
import os
import subprocess

def parcours(name : str):
    liste = os.listdir((name))
    for el in liste:
        new_name = name+'/'+el
        if (os.path.isdir(new_name) and el[0] !='.'):
            parcours(new_name)
        elif (os.path.isfile(new_name)):
            print(new_name+":")
            subprocess.run("java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main "+name + '/' + el, shell=True)
            f = open("./out/tree.dot", "a")
            f.write("}")
            f.close()
            subprocess.run("dot -Tsvg ./out/tree.dot -o ./out/"+el+".svg", shell=True)
            print()
            

print("GOOD : ")
parcours(os.getcwd()+"/"+"Tests"+"/Good")