## SRG0 - An Experiment in Esoteric Computation

SRG0 is a computer. Not a silicone binary traditional "computer" but an abstract machine concept
that runs computations by iteratively applying simple text search and replace rules on an input string.  

The name (I pronounce it "sir-gee-o" or "Sergio" like the famous Spanish golfer) nearly explains the entire concept:

1. (S)earch the input for a rule match.
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

The SRGO computation would be:

```
INPUT: the dog chased the cat 
-- START
the dog chansed the dog
the hello world chased the dog
the hello world chased the hello world
-- END
OUTPUT: the hello world chased the hello world
```


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