## Wnioski co do stosowalności opcji wirtualnej maszyny
-Xms70m -Xmx150m <- zużycie pamięci heap 70MB
-Xms40m -Xmx70m <- zużycie pamięci heap 40MB
-Xms20m -Xmx40m <- zużycie pamięci heap 40MB, pliki wymagały ponownego wczytywania z pliku

-XX:-ShrinkHeapInSteps <- brak zmiany zajętości pamięci heap, ale wymagane ponowne wczytanie plików
-XX:-ShrinkHeapInSteps <- lepsze czyszczenie heap???
-XX:+UseSerialGC <- brak możliwości wczytania cięższych plików
-XX:+UseParNewGC (deprecated) <- błąd kompilacji
-XX:+UseParallelGC <- brak możliwości wczytania cięższych plików
-XX:+UseG1GC <- lepsze czyszczenie heap???