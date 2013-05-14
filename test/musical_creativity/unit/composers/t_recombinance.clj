(ns musical-creativity.unit.composers.t-recombinance
  (:require
   [midje.sweet                               :refer :all]
   [data.chorale                              :as chorale]
   [data.chorale.jsb1                         :refer :all]
   [data.chorale.jsb2                         :refer :all]
   [data.chorale.jsb3                         :refer :all]
   [data.chorale.jsb4                         :refer :all]
   [data.chorale.jsb5                         :refer :all]
   [data.chorale.jsb6                         :refer :all]
   [data.chorale.jsb7                         :refer :all]
   [data.chorale.jsb8                         :refer :all]
   [data.chorale.jsb9                         :refer :all]
   [data.chorale.jsb10                        :refer :all]
   [data.chorale.jsb11                        :refer :all]
   [data.chorale.jsb12                        :refer :all]
   [data.chorale.jsb13                        :refer :all]
   [musical-creativity.composers.recombinance :refer :all]))

(namespace-state-changes (before :facts (do
                                          (reset! *beats-store* {})
                                          (reset! *lexicon-store* {})
                                          (reset! bach-start-beats [])
                                          (reset! bach-rules [])
                                          (reset! bach-compose-beats []))))

(fact "make lexicon name"
  (make-lexicon-name '(57 60 69 76)) => "bach-57-60-69-76")

(fact "find alignemnt in channels"
  (find-alignment-in-all-channels 1000 '(((2 1000) (2 2000) (2 2500)))) => 1000
  (find-alignment-in-all-channels 9000 '(((3 1000) (1 2000) (2 2500)))) => nil)

(fact "find alignment"
  (find-alignment 1000 '((2 1000) (2 2000) (2 2500) (2 3000) (2 3500) (2 4000))) => true)

(fact "all together"
  (all-together '((1 1000) (1 2000) (1 2500) (1 3000)) '(((1000) (2000)))) => 1000)

(fact "triad?"
  (triad? ()) => falsey
  (triad? '((111000 40 500 4 96) (111000 55 500 3 96) (111000 64 1000 2 96) (111000 72 1000 1 96))) => true)

(fact "find triad beginning"
  (create-complete-database '(b43500b))
  (find-triad-beginning) => 'b43500b-14)

(fact "make name"
  (make-name :b206b 1) => 'b206b-1)

(fact "put beat into lexicon"
  (reset! *beats-store* {'b206b-1 {:start-notes [57 60 69 76]}})

  (put-beat-into-lexicon 'b206b-1) => "bach-57-60-69-76"

  @*lexicon-store* => {"bach-57-60-69-76" {:beats '(b206b-1)}})

(fact "build events for beat"
  (create-complete-database ['b5505b]) => true

  (build-events-for-beat 60 'b5505b-60) => ())

(fact "create complete database"
  (create-complete-database ['b206b]) => true

  (keys ('b206b-1 @*beats-store*)) => '(:start-notes :destination-notes :events :voice-leading)
  (count (:events  ('b206b-1 @*beats-store*))) => 4)

(facts "bugs"
  (fact "loading a database"
    (create-complete-database '(b40900b)) => true
    (create-complete-database '(b18806b)) => true
    (println @*beats-store*)))

(fact "get channel numbers from events"
  (get-channel-numbers-from-events '((0 57 1000 4 96) (0 60 1000 3 96))) => '(3 4))

(fact "collect beats"
  (first (collect-beats b206b)) => '([0 57 1000 4 96] [0 60 1000 3 96] [0 69 1000 2 96] [0 76 1000 1 96])
  (second (collect-beats b206b)) => '([1000 59 1000 4 96] [1000 62 1000 3 96] [1000 67 1000 2 96] [1000 79 1000 1 96]))

(fact "collect by timing"
  (collect-by-timing 1000 '((0 57 1000 4 96) (1000 60 1000 3 96))) => '((0 57 1000 4 96)))

(fact "first place where all together"
  (first-place-where-all-together '((0 57 1000 4 96) (0 60 1000 3 96) (0 69 1000 2 96))) => 1000)

(fact "get onset notes from events"
  (get-onset-notes '((0 57 1000 4 96) (0 60 1000 3 96) (0 69 1000 2 96) (0 76 1000 1 96)))
  => '(57 60 69 76))

(fact "get smallest set"
  (get-smallest-set '(0 4 7)) => '(0 4 7))

(fact "get on beat"
  (get-on-beat '((53000 46 1000 4 96) (53000 53 500 3 96) (53000 62 500 2 96) (53000 62 1000 1 96) (53500 52 250 3 96) (53500 55 500 2 96) (53750 50 250 3 96)) 53000)
  => '((53000 46 1000 4 96) (53000 53 500 3 96) (53000 62 500 2 96) (53000 62 1000 1 96)))

(fact "create pitch class set"
  (create-pitch-class-set '(64 67 71)) => '(4 7 11))

(fact "set to zero"
  (set-to-zero '((31000 60 1000 4 96) (31000 67 1000 3 96) (31000 72 1000 2 96) (31000 76 1000 1 96)))
  => '((0 60 1000 4 96) (0 67 1000 3 96) (0 72 1000 2 96) (0 76 1000 1 96)))

(fact "get rules"
  (get-rules '(57 60 69 76) '(59 62 67 79) 'b206b-1)
  => '((3 2 2 b206b-1) (12 2 -2 b206b-1) (7 2 3 b206b-1) (9 2 -2 b206b-1) (4 2 3 b206b-1) (7 -2 3 b206b-1)))

(fact "get last beat events"
  (get-last-beat-events '((18000 48 1000 4 96) (18000 64 1000 3 96) (18000 67 1000 2 96) (18000 72 1000 1 96)))
  => '((18000 48 1000 4 96) (18000 64 1000 3 96) (18000 67 1000 2 96) (18000 72 1000 1 96)))

(fact "break into beats"
  (break-into-beats '((20000 48 2000 4 96) (20000 55 2000 3 96) (20000 64 2000 2 96) (20000 72 2000 1 96)))
  => '((20000 48 1000 4 96) (20000 55 1000 3 96) (20000 64 1000 2 96) (20000 72 1000 1 96) (21000 48 1000 4 96) (21000 55 1000 3 96) (21000 64 1000 2 96) (21000 72 1000 1 96)))

(fact "get re-time"
  (re-time '(((15000 60 1000 4 96) (15000 64 1000 3 96)))) => '(((0 60 1000 4 96) (0 64 1000 3 96))))

(fact "match tonic minor"
  (match-tonic-minor '((5000 55 1000 4 96) (5000 67 1000 3 96) (5000 62 1000 2 96) (5000 71 1000 1 96)))
  => falsey)

(fact "match harmony"
  (match-harmony '(48 67 72 76) '(60 64 67)) => truthy)

(fact "project octaves"
  (project-octaves 60) => '(24 36 48 60 72 84 96 108 120 132))

(fact "reduce it"
  (reduce-it 60 20) => 12)

(fact "all members"
  (all-members '(48 67 72 76) '(24 36 48 60 72 84 96 108 120 132 28 40 52 64 76 88 100 112 124 31 43 55 67 79 91 103 115 127))
  => truthy
  (all-members '(20 30) '(29 20)) => falsey)

(fact "chop into bites"
  (chop-into-bites '((20000 48 2000 4 96) (20000 55 2000 3 96) (20000 64 2000 2 96) (20000 72 2000 1 96)))
  =>
  '(((20000 48 1000 4 96) (21000 48 1000 4 96)) ((20000 55 1000 3 96) (21000 55 1000 3 96)) ((20000 64 1000 2 96) (21000 64 1000 2 96)) ((20000 72 1000 1 96) (21000 72 1000 1 96))))

(fact "remainder"
  (remainder '(76000 41 1500 4 96)) => '((77000 41 500 4 96)))

(fact "remainders"
  (remainders '((77000 53 500 3 96) (77500 50 500 3 96))) => ())

(future-fact "transpose to bachs range"
  (transpose-to-bach-range '((0 60 1000 4 96) (0 64 1000 3 96))) => '((0 64 1000 4 96) (0 68 1000 3 96)))

(fact "wait for cadence"
  (wait-for-cadence '((0 48 1000 4 96) (0 64 1000 3 96) (0 67 1000 2 96) (4100 72 1000 1 96))) => true
  (wait-for-cadence '((0 48 1000 4 96) (0 64 1000 3 96) (0 67 1000 2 96) (0 72 1000 1 96))) => false)

(fact "check for parrellel"
  (check-for-parallel '((0 49 1000 4 96) (0 65 1000 3 96) (0 68 1000 2 96) (0 73 1000 1 96)
                        (1000 48 1000 4 96) (1000 64 1000 3 96) (1000 67 1000 2 96) (1000 72 1000 1 96))) => truthy

  (check-for-parallel '((0 49 1000 4 96) (0 65 1000 3 96) (0 68 1000 2 96) (0 73 1000 1 96))) => false)

(facts "finished composing"
  (fact "when we are not finished"
    (finished-composing? '((0 52 1000 4 96) (0 60 1000 3 96) (0 67 500 2 96) (0 67 500 1 96) (1000 52 1000 4 96) (1000 60 1000 3 96) (1000 67 500 2 96) (1000 67 500 1 96)) true) => false
    (finished-composing? () true) => false)

  (fact "when we are finished"
    (finished-composing? '((0 52 0 4 96) (4001 52 15000 3 96)) true) => true))

(fact "incf the beat"
  (incf-beat "b35300b-42") => "b35300b-3"
  (incf-beat 'b32100b-31) => "b32100b-2"
  (incf-beat nil) => nil)

(fact "get db name"
  (get-db-name "b35300b-42") => "b35300b")

(fact "find 1000s"
  (find-1000s '((3000 61 1000 1 96) (3000 69 1000 2 96) (3000 69 1000 3 96) (3000 69 1000 4 96)))
  => 3000)

(fact "find 2000s"
  (find-2000s '((3000 61 1000 4 96) (3000 69 1000 3 96)))
  => nil)

(fact "get region"
  (get-region 0 4000 '((3000 61 1000 4 96) (3000 69 1000 3 96) (4001 70 1000 96)))
  => '((3000 61 1000 4 96) (3000 69 1000 3 96)))

(fact "distance to cadence"
  (distance-to-cadence '((3000 61 1000 1 96) (3000 69 1000 2 96) (3000 69 1000 3 96) (3000 69 1000 4 96)))
  => 3000)

(fact "discover cadences"
  (discover-cadences '((4000)) '((3000 61 1000 1 96) (3000 69 1000 2 96) (3000 69 1000 3 96) (3000 69 1000 4 96)))
  => '((3000 61 1000 1 96) (3000 69 1000 2 96) (3000 69 1000 3 96) (3000 69 1000 4 96)))

(fact "find cadence place"
  (find-cadence-place '((3000 61 1000 4 96) (3000 69 1000 3 96))) => nil
  (find-cadence-place '((25000 57 1000 4 96) (25000 64 1000 3 96) (25000 69 1000 2 96) (25000 72 1000 1 96))) => '(25000))

(fact "find best on time"
  (find-best-on-time '(0 1000 2000)) => 1000)

(fact "remove all"
  (remove-all '(1 2 3) '(3 4 2 1 5 6 3)) => '(4 5 6))

(fact "resolve beat"
  (resolve-beat '((0 0 1000 0 0))) => '((0 0 1000 0 0))
  (resolve-beat '((3000 61 1000 4 96) (3000 69 1000 3 96))) => '((3000 61 1000 4 96) (3000 69 1000 3 96))

  (resolve-beat '((30000 55 1000 4 96) (30000 60 500 3 96) (30000 64 1000 2 96) (30000 67 1000 1 96) (30500 57 500 3 96)))
  => '((30000 55 1000 4 96) (30000 60 1000 3 96) (30000 67 1000 1 96) (30000 64 1000 2 96)))

(fact "positions"
  (positions 1 '(4 5 3 1 2 1 4 5))
  => '(3 5))

(fact "check-mt"
  (check-mt '((0 43 1000 4 96) (0 59 1000 3 96) (0 62 1000 2 96) (0 67 1000 1 96)))
  => false)