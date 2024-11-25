# NMS Inspector
Utility plugin for plugin developers to inspect the Java classes and methods
on runtime.

## Getting Started
**This plugin supports only PaperMC servers**

Start by obtaining a copy of my plugin at the [releases](https://github.com/KoblizekXD/nms-inspector/releases)
section of the repository. After downloading the plugin, place it in the `plugins` directory
on your Minecraft server.

### Usage

The command features 1 permission(`nmsinspector.allow`) which is given to OPs by default
and is required to execute any command.

The plugin depends on the `Vineflower` library, which should be downloaded by your
server automatically.

#### Commands

- `/classinfo <class> [filter]` - Displays summary of information about the `class`.
The `filter` is an optional parameter which can be any of these values:
    - `none` - Will not display any additional information.
    - `methods` - Displays all methods of the class.
    - `fields` - Displays all fields of the class.
    - `constructors` - Displays all constructors of the class.
    - `*` - Displays all methods, fields and constructors of the class.  
The default value for `filter` is `none`.
- `/classdata <class> [methods...]` - Decompiles the given class and displays the source code.
The `methods` parameter is an optional parameter which can be used to specify which methods
to keep in the decompiled source code. If no methods are specified, all methods will be kept.
- `/packageinfo <package>` - Displays summary of information about the `package`, as well as
all classes in the given package(non-recursive).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.