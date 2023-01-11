# Transactional Key Value Store

The interface should allow the user to perform operations in transactions, which allows the user to commit or roll back their changes to the key value store. That includes the ability to nest transactions and roll back and commit within nested transactions. The interface support the following commands:

```markdown
SET <key> <value> // store the value for key
GET <key>         // return the current value for key
DELETE <key>      // remove the entry for key
COUNT <value>     // return the number of keys that have the given value
BEGIN             // start a new transaction
COMMIT            // complete the current transaction
ROLLBACK          // revert to state prior to BEGIN call
```

## Transactional Key Value Store
Implementation details: 
Each transaction has its own storage. When a new transaction is created, it is placed on the stack and a new storage is created for it. When we need to rollback transaction we delete transaction and their local storage. In case of commit we merge parent transaction store with current and remove transaction. 

Commands are executed using the CommandExecutor. Each command has its own executor with command execution logic. All executors are created through the CommandExecutorFactory. The factory knows how to create executors with the necessary dependencies. The executor lives in the transaction scope and always has the necessary repository to work with the transaction local storage. 

Ui has writen on Compose

<img src="https://user-images.githubusercontent.com/9141417/211314451-737011fe-55b6-4172-8f42-b8ee6f266c73.png" width="300">
