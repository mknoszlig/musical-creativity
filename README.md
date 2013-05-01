#Computer models of musical creativity

[![Build Status](https://travis-ci.org/josephwilk/musical-creativity.png?branch=master)](https://travis-ci.org/josephwilk/musical-creativity)

Experiments with models for computers generating music.

##Example

```clojure
(require '[musical-creativity.musician :as musician] :reload)
(use '[musical-creativity.instruments])

;Cosine
(require '[musical-creativity.composers.cosine :as cosine] :reload)
(musician/play (cosine/compose) piano)

;Cellular automata
(require '[musical-creativity.composers.cellular-automata :as ca] :reload)
(musician/play (ca/compose) piano)
(musician/play (ca/compose ca/rule-22) piano)

;Sonify data
(require '[musical-creativity.composers.sonify-data :as sonify-data] :reload)
(musician/play (sonify-data/compose) piano)

;Sonify text
(require '[musical-creativity.composers.sonify-words :as sonify-words] :reload)
(musician/play (sonify-words/compose "potato potato potata potatd potate potatk") piano)

;Markov chains
(require '[musical-creativity.composers.markov :as markov] :reload)
(musician/play (markov/compose) piano)
(musician/play (markov/compose) ping)

(musician/play (markov/compose data.bach/bach1 61 50) piano)

;Fuzzy
(require '[musical-creativity.composers.fuzzy :as fuzzy] :reload)
(musician/play (fuzzy/compose) piano)

;Gradus
(require '[musical-creativity.composers.gradus :as gradus] :reload)

(musician/play (gradus/compose) piano)
(musician/play (gradus/compose-canon) piano)
```

##Book

Based on exercises from:

![Computer Models of Musical Creativity](http://mitpress.mit.edu/covers/9780262033381.jpg)

Computer Models of Musical Creativity http://mitpress.mit.edu/books/computer-models-musical-creativity
