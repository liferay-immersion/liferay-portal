This directory contains files used by Documents and Media that must be safely
backed up and restored.

Adaptive Media files (e.g. Documents and Media previews and thumbnails) may be
excluded from the backup because they can regenerated on demand as users request
them. These files exist in paths that match the pattern "/[0-9]+/0/adaptive"
(e.g. "/20097/0/adaptive"). You can safely use this pattern to exclude these
files from your backups.

Removing (or failing to restore) any other file except Adaptive Media files will
produce failures. Make sure that only Adaptive Media files are excluded from
your backups.