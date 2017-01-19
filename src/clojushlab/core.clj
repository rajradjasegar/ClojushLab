(ns clojushlab.core
  (:require [instaparse.core :as insta]))

(def vec-parser
  (insta/parser
    "instrs = ((assign? | expr?) <';'> <space>?)*
     
     (* expression *)
     expr = (<space>? (assign | operation) <space>?)*
     
     (* Affectation *)
     assign = var <space>? <'='> <space>? (operation | number)  
     
     (* variables *)
     var = #'[a-zA-Z0-9]+'
     
     (* operations *)
     operation = matrix <space>? op <space>? matrix

     (* operateurs *)
     op = '*' | '+' | '-'

     (* matrices *)
     matrix = <'m('> <space>? matrow* <space>? <')'>

     (* Represente une ligne de la matrice *)
     matrow = <'['> <space>? (number <space>)*  <']'>

     (* Les espaces *)
     <space> = #'[\\s]*'

     (* Les constantes de 0 à 9 *)
     number = #'[0-9]+'"
  ))

(vec-parser "m([5454 151 484 4]) * m([5454 151 484 4]); v = m([1 2 5 8 7]) + m([1]);m([4]) * m([2]);")