(ns clojushlab.core
  (:require [instaparse.core :as insta]))

(def matrice-parser
  "a matrix computation parser"
  (insta/parser
    "expr = space? (assig | operation | number | varget) space? <';'> space?
     assig = varname space? <'='> space? elt 
     
     (* operation avec parenthèse ou non *)
     <woperation> = <'('> elt space op space elt <')'>
     <noperation> = elt space op space elt

     (* operations *)
     operation = noperation | woperation
     
     (* Termes de l'operation *)  
     <elt> = number | negnum | operation | varget | matrix

	(* operateurs *)
     op = '*' | '+' | '-' | '^'

     (* matrices *)
     matrix = <'m['> (space? matrow space?)* <']'>

     (* Represente une ligne de la matrice *)
     matrow = <'{'> (space? elt space?)*  <'}'>

     (* Les espaces *)
     <space> = <#'[ ]+'>
     
     negnum = <'-'> number
     (* Les nombres composé de chiffres de 0 à 9 *)
     number = <'+'>? #'[0-9]+'

     varget = varname 

     (* variables *)
     varname = #'[a-zA-Z]\\w*'"
  ))
;(* operations *) est ce qu'on peut l'ajouter ?
;operation = matrix <space>? op <space>? number

(defn add [op el1 el2] 
(mapv #(mapv op %1 %2) el1 el2))

 (defn transpose
  [s]
  (apply map vector s))
 
(defn nested-for
  [f x y]
  (map (fn [a]
         (map (fn [b] 
                (f a b)) y))
       x))
(defn mul
  [el1 el2]
  (nested-for (fn [x y] (reduce + (map * x y))) el1 (transpose el2)))
(defn mat-power [m1 power] 
  (def b m1)
 (loop [x power]
 (when (> x 1)
   (def b (mul m1 b))
 
   (recur (- x 1))))
(println b)
)


(defn execute 
  [el1 op el2]
  (case op
        [:op "*"] (mul el1 el2)
        [:op "+"] (add + el1 el2)
        [:op "-"] (add - el1 el2)
        [:op "^"] (mat-power el1 el2 )
        "Unknown operator"))

(def matrice-interpret
  {
   :number #(Long/parseLong %)
   :negnum #(* -1 %1)
   :matrow (comp vec list)
   :matrix (comp vec list)
   :operation execute
   })

;(insta/transform matrice-interpret (matrice-parser "(m[ {1 2 3} {4 +5 6}] + m[ {7 8 9} {10 -11 12}]) * m[ {1 8 9} {1 2 6} {2 4 5}];"))
;(matrice-parser "m[ {7 2 3} {4 5 6}] + m[ {7 8 9} {10 11 12} ]  - m[ {13  14 15} {16 17 18}]  ];")

(insta/transform matrice-interpret 
               (matrice-parser " ((m[{0 0} {1 0}] + m[{2 1} {0 2}]) ^ 2)  ;"))
