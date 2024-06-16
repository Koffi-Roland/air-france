#ifndef COMMON_HASHER
#define COMMON_HASHER

class string_hash
{
public:
  typedef unsigned (*hasher)(const std::string& s);

  string_hash( hasher h )
    : _hash( h )
  { }

  unsigned operator() ( const std::string& str ) const
  {
    return _hash( str );
  }

private:
  hasher _hash;
};

class long_hash
{
public:
  typedef unsigned (*hasher)(const long& s);

  long_hash( hasher h )
    : _hash( h )
  { }

  unsigned operator() ( const long& str ) const
  {
    return _hash( str );
  }

private:
  hasher _hash;
};

class char_hash
{
public:
  typedef unsigned (*hasher)(const char& s);

  char_hash( hasher h )
    : _hash( h )
  { }

  unsigned operator() ( const char& str ) const
  {
    return _hash( str );
  }

private:
  hasher _hash;
};

#endif
