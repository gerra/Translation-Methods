$lol='(?:[A-Z][A-Z0-9+-\.]*:\/\/)?(?<url>([A-Z0-9][A-Z0-9_-]*(\.[A-Z0-9][A-Z0-9_-]*)*))(:\d+)?(\/.*)?';
sub uniq {
    my %seen;
    grep !$seen{$_}++, @_;
}
 
my @matches;
while ($s = <STDIN>) {
    push (@matches,$+{url}) while ($s =~ m/(<\s*a.*href\s*=\s*\")($lol)(\".*>)/ig);
}
@matches = uniq(@matches);
@matches = sort @matches;
foreach (@matches) {
  print "$_\n";
}
