local val = redis.call(""get"", KEYS[1])
if val == ARGV[1] or val == nil then
    redis.call(""set"", KEYS[1], ARGV[2])
    return 1
else
    return 0
end