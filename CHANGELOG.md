#### 0.1-dumb

dumbest implementation, every time rescan the whole program to find the matching bracket

#### 0.2-map_jump_cache

keeps an internal cache to map bracket pairs, so the program is scanned only the first time a bracket is found

#### 0.3-map_jump_precache

keeps an internal cache to map bracket pairs with entries computed before program execution

