(ns clojushlab.core
  (:require [instaparse.core :as insta]))

(def vec-parser
  (insta/parser
    "
     instrs = ((assign? | expr?) <';'> <space>?)*
     
     (* expression *)
     expr = (<space>? (assign | operation) <space>?)*
     
     (* Affectation *)
     assign = var <space>? <'='> <space>? (operation | number)  
     
     (* operations *)
     operation = matrix <space>? op <space>? matrix
 
     (* operateurs *)
     op = '*' | '+' | '-'

     (* matrices *)
     matrix = <'m('> <space>? matrow* <space>? <')'>

     (* Represente une ligne de la matrice *)
     matrow = <space>? <'['> <space>? (number <space>)*  <']'> <space>?

     (* Les espaces *)
     <space> = #'[\\s]*'

     (* Les constantes de 0 à 9 *)
     number = #'[0-9]+'

     (* variables *)
     var = #'[a-zA-Z0-9]+'"
  ))

(vec-parser "v = m([1 2 5] [1 2 5]) * m([1 2 5] [1 2 5] ) ;  m([4]) * m([2]);")