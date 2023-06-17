1) need to add variables
2) for good, of course, it would be worth taking out configs in config files
3) make spring security and in the same place we need to take data about the user, and not transfer the user ID (like I did)
4) we can connect swagger here
5) we assume that we already have users, so I created a migration with existing users
6) we can also make a global error handler (more hadnle of errors)
7) Can be extended to multiple currencies (ENUM)
8) after the removal, we can add some more timing, for example, 30 seconds after that, nothing can be removed, we never know there are other JOBs
9) we can make your own exceptions, since I have places where you can return NotFoundException for example
10 we can, in principle, move all the necessary variables into variables
11) errors can be extracted into files and given to the client with different languages if language selection is enabled
12) we can add transactions to the history that the user tried but they did not work, for audit, as you described in the task
13) we can make an API to reset the lock, where it will be approved by the administrator
14) we still need to think about what to do if a person made 7 transactions within an hour, we marked him as suspicious but when to remove this mark?
15) we can do a lot of cases in tests, for different checks but I covered only some cases, I think that this is enough
16) noticed one bug with the trigger but if we don't notice it then it's great :)






1) the question about personal data when creating a wallet is a little unclear
in my case, when we create a wallet, we also create a user at the same time? (Weird)
so I made it so that we already have users in the database