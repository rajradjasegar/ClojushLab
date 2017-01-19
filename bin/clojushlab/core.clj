(ns clojushlab.core
  (:require [instaparse.core :as insta]))

(def matrice-parser
  (insta/parser
    "
     <instrs> = ((assign? | expr?) <';'> <space>?)*
     
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

(defn add-mat [op m1 m2] 
  ;(def number_ligne (count m1))
  (def index (count '(concat (nth m1 0) (nth m1 1))))
  (split-at index (map op (concat (nth m1 0) (nth m1 1)) (concat (nth m2 0) (nth m2 1)))))

(defn sub-mat [op m1 m2] 
  ;(def number_ligne (count m1))
  (def index (count '(concat (nth m1 0) (nth m1 1))))
  (split-at index (map op (concat (nth m1 0) (nth m1 1)) (concat (nth m2 0) (nth m2 1)))))

(defn mul-mat
  [m1 m2])

(defn execute 
  [m1 op m2]
  (case op
        [:op "*"] (mul-mat m1 m2)
        [:op "+"] (add-mat + m1 m2)
        [:op "-"] (sub-mat - m1 m2 )
        "Unknown operator"))

(def matrice-interpret
  {
   :number #(Long/parseLong %)
   :matrow (comp list)
   :matrix (comp vec list)
   :expr (comp)
   :operation execute
   })

;(insta/transform matrice-interpret (matrice-parser "m( [1 2 5]  [1 2 5])  - m( [1 8 6] [4 8 1] ); m( [1 2 5]  [1 2 5])  - m( [1 1 6] [4 4 1] );"))
(insta/transform matrice-interpret (matrice-parser "m( [1 2 5]  [1 2 5])  + m( [1 8 6] [4 8 1] );"))