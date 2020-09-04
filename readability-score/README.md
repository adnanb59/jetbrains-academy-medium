## readability-score

This tool serves to evaluate a piece of text and provide various statistics (such as word count, syllables, etc.) as well as different readability scores to determine it's difficulty and for which age it's suitable for.

The stats provided are:
- words
- sentences
- characters
- syllables
- polysyllables (number of words containing 3+ syllables)

These readability metrics are:
- [Automated Readability Index (ARI)](https://en.wikipedia.org/wiki/Automated_readability_index) - calculated by `score = 4.71*characters/words + 0.5*words/sentences - 21.43`
- [Flesch-Kincaid (FK) grade level](https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests#Flesch%E2%80%93Kincaid_grade_level)- calculated by `score = 0.39*(words/sentences) + 11.8*(syllables/words) - 15.59`
- [Simple Measure of Gobbledygook (SMOG) index](https://en.wikipedia.org/wiki/SMOG) - calculated by `score = 1.043*sqrt(polysyllables*30/sentences) + 3.1291`
  > In real life, calculation requires more than 30 sentences but that wasn't followed here
- [Coleman–Liau (CL) index](https://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index) - calculated by `score = 0.0588*L - 0.296*S - 15.8` where L is the average number of characters per 100 words and S is the average number of sentences per 100 words

Additionally, the recommended age displayed by program comes from a table (refer to ARI link) which maps ARI scores to ages.

#### Running program
After compiling, run program using `java Runner [filename]` where `filename` is a text file containing the text to evaluate.
The program will first print the text, followed by text statistics.

Afterwards, user will then be prompted for which readability metric they want to see.

The prompt is: `Enter the score you want to calculate (ARI, FK, SMOG, CL, all): `, where `all` will display all of them. 

After printing the metrics, the recommended age for the text will be displayed.


##### URL: https://hyperskill.org/projects/39
