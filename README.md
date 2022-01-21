#SRG0 - Search Replace Go 0 - pronounced as in the name "Sergio"

SRG0 is an experiment in esoteric programming languages.

The name just about explains the entire "computer".  You give SRG0 an input string and an ordered list of 
simple search and replace rules.  No regular expressions, no variables.  SRG0 loops through the list of
rules until a "search" string matches the input.  The "replace" value is swapped into input (the first 
occurrence only) and then you "Go 0" meaning SRG0 goes back to the start of the rule list and loops again.
The program halts when no more rules match.

##Rule File (aka Your Program) Format
```
-- use to dashes to add a comment that will be printed out whenever a rule is applied.
searchString -> replaceString //slashes can be used after the replaceString to add comments to your "code"
```

##Pending Rule File Updates

global a b c def - these values may NOT be declared as local tokens as various files may use them

local x A CD     - these tokens will be replaced by other tokens that don't conflict with other files local tokens