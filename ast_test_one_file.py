#!/usr/bin/python3
import os
import subprocess

def parcours(name : str):

    new_name = name
    if (os.path.isfile(new_name)):
        print(new_name+":")
        subprocess.run("java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main "+name, shell=True)
        f = open("./out/tree.dot", "a")
        f.write("}")
        f.close()
        subprocess.run("dot -Tsvg ./out/tree.dot -o ./out/"+name+".svg", shell=True)
        print()
        
input_file = input("Enter the path of the file to test: ")
parcours(os.getcwd()+"/Tests/Good/Types"+"/"+input_file)