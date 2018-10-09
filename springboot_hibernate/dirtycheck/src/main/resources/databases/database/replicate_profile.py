#!/usr/bin/env python3

import sys


def main(number_of_rep):
    profile_seq = "::PRFE_ID_SEQ::"
    file_name = "insert_profile.sql"

    template_lines = []
    with open('template_{}'.format(file_name),'rt') as source:
        template_lines = source.readlines()

    with open(file_name,'wt') as target:
        for seq in range(0, number_of_rep):
            for template_line in template_lines:
                target.write(template_line.replace(profile_seq, "{}".format(seq+1)))
                if (seq + 1) % 1000 == 0:
                    target.write("COMMIT;\n")
        target.write("COMMIT;\n")


if __name__ == '__main__':
    main(int(sys.argv[1]))
