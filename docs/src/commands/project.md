
# `project`

## Description

Manages Java projects with various subcommands.

## Usage

```bash
buildcli project [subcommand] [options] [arguments]
```

## Subcommands

| Subcommand      | Description                                 |
|-----------------|---------------------------------------------|
| `add, a`        | Adds a new project.                         |
| `remove, rm`    | Removes an existing project.                |
| `build, b`      | Builds the project.                         |
| `set`           | Sets project properties.                    |
| `test, t`       | Tests the project.                          |
| `run`           | Runs the project.                           |
| `init, i`       | Initializes a new project.                  |
| `cleanup, clean`| Cleans up the project.                      |
| `update, up`    | Updates the project.                        |
| `document-code, docs` | Documents the project code.           |

## Options

| Option          | Description                                 |
|-----------------|---------------------------------------------|
| `-h, --help`    | Display help information about the command. |
| `-V, --version` | Display the version of the command.         |

## Examples

### Example 1

Initialize a new project.

```bash
buildcli project init
```

## See Also

- [autocomplete](autocomplete.md)
- [about](about.md)