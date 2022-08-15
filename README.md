# MergeSortedFiles

Эта программа выполняет сортировку слиянием нескольких файлов.

Входные файлые могут содеражть данные одного из вдух видов: целые числа или строки. Строки могут содержать любые не пробельные
символы, строки с пробелами считаются ошибочными.

Результатом работы программы является новый файл с объединенным содержимым
входных файлов, отсортированный по возрастанию или убыванию путем сортировки слиянием.

Если в исходных файлах нарушен порядок сортировки, производится частичная сортировка, ошибочные данные не попадают в выходной файл.
При реализации не использовались библиотечные функции сортировки, алгоритм устойчив к большим файлам т.к содержимое файлов не буферизуется.

Инструкция по запуску указана в файле *Start_Instruction.md*.
