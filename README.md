# Task_Allocation
Consider a developers team in a large company, where the team is assigned with different
tasks, and each team member can perform one task at a time. The question arises: which
task should we allocate a team member that becomes available? One approach, “first-in,
first-out,” is to allocate the tasks according to the time they were created. In this approach,
all tasks are performed within a reasonable amount of time. However, some of the tasks
have higher priorities, and you may want to dedicate one of the senior team members to
solve these tasks first.
To handle these constraints, you will design a data structure to simulate the tasks
allocation. Tasks may be allocated according to the order of creation or according to the
priority of the task. The main challenge arises from the fact that tasks must be kept
according to two different orders. Once, according to their priority and once according to
their creation time. Towards maintaining this data, we will use two data structures. A
1
