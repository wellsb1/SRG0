## SRG0 - An Experiment in Esoteric Computation

SRG0 is a computer. Not a silicone binary traditional "computer" but an abstract machine concept
that runs computations by iteratively applying simple text search and replace rules on an input string.  

The name SRG0, I pronounce it "ser-gio" or "Sergio" like the famous Spanish golfer, nearly explains the entire concept.  
Given a string input and a set of static ordered search/replace rules:
1. (S)earch the input for a substring match.
2. (R)eplace the first occurrence of the fist rule to match with the rule's replacement text. 
3. After matching/replacing a rule, (G)o back to the start of your rule list (rule 0) and loop again.
4. Computation ends when no more rules match and the transformed input is returned as output.

Here is a simple SRG0 program:

```
cat -> dog
dog -> hello world
```

Given the input string: 
```
the dog chased the cat
```

The SRG0 computation would be:

```
INPUT: the dog chased the cat 
-- START
the dog chansed the dog
the hello world chased the dog
the hello world chased the hello world
-- END
OUTPUT: the hello world chased the hello world
```

## So What is the Point?

I'm a computer nerd who like puzzles. I like exploring the definitions of "computation" such as where I have heard
Stephen Wolfram describe computation as simply "applying rules to data."  I have found myself sucked down the internet
rabbit hole many nights investigating things like cellular automata, 
[Conway's game of life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life),  
[Wolfram's hypergraph model](https://writings.stephenwolfram.com/2020/04/finally-we-may-have-a-path-to-the-fundamental-theory-of-physics-and-its-beautiful), 
[esoteric programming languages](https://esolangs.org/), 
and [single bit computing](https://en.wikipedia.org/wiki/1-bit_computing).  
I am also very interested in alternative programming paradigms such as [Orca](https://github.com/hundredrabbits/Orca) 
which lets you see all of your code and data running and interacting together.

I found myself wondering what a "simple" Turing complete model of computation that allowed easy visualization might look like.
The Wolfram hypergraph is super simple in its design, but it is amazingly difficult to "think" about how to do practical computation
with the model.  

I thought what if I "replaced the graph model with a simple "string of text" and kept the concept of
iteratively applying the simplest possible transformation rules to the string.  So the question was born.  Can you build
a Turing complete "computer" based on the concept of iteratively applying string search and replace rules?

As a proof of concept, I was able to get SRG0 to do simple decimal addition.  
Given the input string "7+5" I was able to find a [set of rules](https://github.com/wellsb1/SRG0/blob/main/src/test/resources/com/github/wellsb1/srg0/addition.rules), 
that would transform the input into the output string "12".

With the POC in hand, I suspected that SRG0 was Turing complete.  To be Turing complete, a language needs:
1. A form of conditional repetition or conditional jump
2. A way to read and write some form of storage

Executing a search/replace match achieves both of these conditions. The search/replace is the storage read/write, and
after every match/replace you "jump" back to the first instruction.  So each SRG0 program is basically a program with
infinitely many GOTO statements but only one target label...back to start of the rule list.

I'm no math mathematician, so I don't know how to mathematically prove SRG0 is Turing complete, 
but I love puzzles, and I know that if you can write a program for machine A to model machine B, and machine B
is known to be Turing complete, then it is a given that machine A is also Turing complete.

So...the journey continues.  I have started a rule set to try to implement a "subleq" machine which is known to be 
Turing complete.  So far, I have an input model to represent traditional binary memory and a rule set that will
start from an instruction pointer and "fetch register A".  I have hilariously gone backwards from a nearly human
understandable representation of a computation in string manipulation form to a nearly unintelligible use case searching
and replacing within a string of mostly 1's and 0's!

Fun stuff born out potentially misguided intellectual curiosity!



## Rule File (aka Your Program) Format
```
-- use to dashes to add a comment that will be printed out whenever a rule is applied.
searchString -> replaceString //slashes can be used after the replaceString to add comments to your "code"
```


## Ok...But Why?
TODO


## Pending Rule File Updates

global a b c def - these values may NOT be declared as local tokens as various files may use them

local x A CD     - these tokens will be replaced by other tokens that don't conflict with other files local tokens