(ns clojushlab.core
  (:require [instaparse.core :as insta]))

(def matrice-parser
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

(defn add-mat 
  [m1 m2])

(defn sub-mat
  [m1 m2])

(defn mul-mat
  [m1 m2])

(defn execute 
  [m1 op m2]
  (case op
        [:op "*"] "mul"
        [:op "+"] "add"
        [:op "-"] "minus"
        "default"))

(def matrice-interpret
  {
   :number #(Long/parseLong %)
   :operation execute
   })
(insta/transform matrice-interpret (matrice-parser "m([1 2 5] [1 2 5]) * m( [1 2 5] [1 2 5] );"))