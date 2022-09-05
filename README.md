# DzialkiPlugin

A Minecraft plugin to claim, protect, and manage access to land plots.

# Available commands:

- `dz_create name sizeX sizeZ` - claims a plot giving it a _name_ and a size of _sizeX_, _sizeZ_
- `dz_delete name` - vacates a plot with a given _name_
- `dz_add user name` - adds _user_ to allowed list on plot _name_
- `dz_remove user name`  - removes _user_ from allowed list on plot _name_

# Admin commands:

- `getlist` - prints a list of all claimed land plots
- `dz_max_size number` - changes the max size of every plot (default 100 x 100)
- `dz_max_plots number` - changes the max number of plots a user can have (default 2)




