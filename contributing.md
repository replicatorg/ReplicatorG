# Contributing to RepG

Yay! Thanks for taking the time to help us out!
This document has some guidelines on how to make your contribution easiest
for us to review and integrate. These are guidelines, not rules, so use your best judgment
and feel free to propose changes to this document in a pull request.

This project adheres to the [Contributor Covenant 1.2](http://contributor-covenant.org/version/1/2/0/). By participating, you are
expected to uphold this code. Please report any unacceptable behavior to liam@cpan.org.

## Submitting issues

* You can create an issue on [our issue tracker](https://github.com/replicatorg/ReplicatorG/issues/),
  but before doing that, please read the following notes on debugging and submitting issues,
  and include as many details as possible with your report.
  Also, we like it when people read [How To Ask Questions](http://www.catb.org/esr/faqs/smart-questions.html).
* Include the version of ReplicatorG (or the shasum of the commit you built off of), and the OS.
* Include screenshots and animated GIFs when possible; they're *incredibly* helpful.
* Include the behavior you expected, and the *exact* deviation that occured.
* Include all stack traces.
* On Mac, check Console.app for stack traces to include if reporting a crash.
* If at all possible, please try to use a debugger to find out what line it crashes on, and include that.
* Perform a [cursory search](https://github.com/replicatorg/ReplicatorG/search?q=&type=Issues)
  to see if a similar issue has already been submitted.

## Pull requests

* Include screenshots and animated GIFs in your pull request whenever possible.
* Follow the [Java](https://google.github.io/styleguide/javaguide.html) and [Groovy](http://www.groovy-lang.org/style-guide.html) style guides.


## Git commit messages

* Use the present tense ("Add feature", not "Added feature")
* Use the imperative mood ("Change color to...", not "Changes color to...")
* Limit the first line to <72 characters.
* Reference issues and pull requests liberally.
* Consider starting the commit message with an applicable emoji (borrowed from atom):
    * :art: `:art:` when improving the format/structure of the code
    * :racehorse: `:racehorse:` when improving performance
    * :non-potable_water: `:non-potable_water:` when plugging memory leaks
    * :memo: `:memo:` when writing docs
    * :penguin: `:penguin:` when fixing something on Linux
    * :apple: `:apple:` when fixing something on Mac OS
    * :checkered_flag: `:checkered_flag:` when fixing something on Windows
    * :bug: `:bug:` when fixing a bug
    * :fire: `:fire:` when removing code or files
    * :green_heart: `:green_heart:` when fixing the CI build
    * :white_check_mark: `:white_check_mark:` when adding tests
    * :lock: `:lock:` when dealing with security
    * :arrow_up: `:arrow_up:` when upgrading dependencies
    * :arrow_down: `:arrow_down:` when downgrading dependencies
    * :shirt: `:shirt:` when removing linter warnings
